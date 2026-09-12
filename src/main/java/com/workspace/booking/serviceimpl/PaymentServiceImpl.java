package com.workspace.booking.serviceimpl;

import com.workspace.booking.common.enums.BookingStatus;
import com.workspace.booking.common.enums.ErrorCode;
import com.workspace.booking.common.enums.ManualDepositMethod;
import com.workspace.booking.common.enums.PaymentStatus;
import com.workspace.booking.common.enums.PaymentTransactionType;
import com.workspace.booking.common.exception.CustomException;
import com.workspace.booking.dto.payment.PaymentRequest;
import com.workspace.booking.dto.payment.PaymentResponse;
import com.workspace.booking.dto.payment.PaymentStatusUpdateRequest;
import com.workspace.booking.entity.booking.Booking;
import com.workspace.booking.entity.finance.Payment;
import com.workspace.booking.entity.identity.User;
import com.workspace.booking.mapper.PaymentMapper;
import com.workspace.booking.repository.BookingRepository;
import com.workspace.booking.repository.PaymentRepository;
import com.workspace.booking.repository.UserRepository;
import com.workspace.booking.service.EmailService;
import com.workspace.booking.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final PaymentMapper mapper;
    private final EmailService emailService;

    @Override
    @Transactional
    public PaymentResponse submitPayment(
            Long bookingId,
            PaymentRequest request
    ) throws IOException {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOKING_NOT_FOUND));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new CustomException(ErrorCode.VALIDATION_ERROR, "Cannot submit payment for cancelled booking");
        }
        if (paymentRepository.existsByBookingIdAndTransactionType(bookingId, PaymentTransactionType.PAYMENT)) {
            throw new CustomException(ErrorCode.VALIDATION_ERROR, "Payment already submitted for this booking");
        }
        if (request.screenshot() == null || request.screenshot().isEmpty()) {
            throw new CustomException(ErrorCode.VALIDATION_ERROR, "Transaction screenshot is required");
        }
        if (request.screenshot().getContentType() == null || !request.screenshot().getContentType().startsWith("image/")) {
            throw new CustomException(ErrorCode.INVALID_FILE_TYPE);
        }

        Payment payment = Payment.builder()
                .booking(booking)
                .user(booking.getUser())
                .transactionType(PaymentTransactionType.PAYMENT)
                .status(PaymentStatus.PENDING)
                .depositMethod(request.depositMethod())
                .paidToNumber(request.paidToNumber())
                .senderNumber(request.senderNumber())
                .referenceCode(request.referenceCode())
                .amount(request.amount())
                .screenshotBlob(request.screenshot().getBytes())
                .screenshotMimeType(request.screenshot().getContentType())
                .screenshotFilename(request.screenshot().getOriginalFilename())
                .build();

        return mapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getAll(Pageable pageable) {
        return paymentRepository.findAllByOrderByCreatedOnDesc(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getByStatus(PaymentStatus status, Pageable pageable) {
        return paymentRepository.findByStatusOrderByCreatedOnDesc(status, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getByTransactionType(PaymentTransactionType transactionType, Pageable pageable) {
        return paymentRepository.findByTransactionTypeOrderByCreatedOnDesc(transactionType, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public PaymentResponse updateStatus(Long id, PaymentStatusUpdateRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.VALIDATION_ERROR, "Payment not found"));

        if (request.status() == PaymentStatus.PENDING) {
            throw new CustomException(ErrorCode.VALIDATION_ERROR, "Admin can only confirm or reject payments");
        }
        if (request.status() == PaymentStatus.REJECTED
                && (request.reasonOfReject() == null || request.reasonOfReject().isBlank())) {
            throw new CustomException(ErrorCode.VALIDATION_ERROR, "Reject reason is required");
        }

        User admin = null;
        if (request.adminId() != null) {
            admin = userRepository.findById(request.adminId())
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        }

        payment.setStatus(request.status());
        payment.setReasonOfReject(request.reasonOfReject());
        payment.setAdminDecisionBy(admin);
        payment.setAdminDecisionAt(LocalDateTime.now());

        Booking booking = payment.getBooking();
        if (request.status() == PaymentStatus.CONFIRMED) {
            if (payment.getTransactionType() == PaymentTransactionType.PAYMENT) {
                booking.setStatus(BookingStatus.CONFIRMED);
                bookingRepository.save(booking);
                sendPaymentConfirmedEmail(payment);
            } else {
                sendRefundConfirmedEmail(payment);
            }
        } else {
            sendPaymentRejectedEmail(payment, request.reasonOfReject());
        }

        return mapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    @Scheduled(fixedDelay = 600000)
    @Transactional
    public void cancelExpiredUnpaidBookings() {
        LocalDateTime deadline = LocalDateTime.now().minusHours(24);
        for (Booking booking : bookingRepository.findByStatusAndCreatedOnBefore(BookingStatus.PENDING, deadline)) {
            boolean hasPaymentForm = paymentRepository.existsByBookingIdAndTransactionType(
                    booking.getId(),
                    PaymentTransactionType.PAYMENT
            );
            if (!hasPaymentForm) {
                booking.setStatus(BookingStatus.CANCELLED);
                booking.setCancelledAt(LocalDateTime.now());
                booking.setCancelReason("Payment was not submitted within 24 hours");
                bookingRepository.save(booking);
                log.info("Cancelled unpaid booking id={} after 24 hours", booking.getId());
            }
        }
    }

    private void sendPaymentConfirmedEmail(Payment payment) {
        emailService.sendEmail(
                payment.getUser().getEmail(),
                "Payment confirmed",
                "Your payment for booking " + payment.getBooking().getBookingRef()
                        + " has been confirmed. Your booking is now confirmed."
        );
    }

    private void sendPaymentRejectedEmail(Payment payment, String reason) {
        emailService.sendEmail(
                payment.getUser().getEmail(),
                "Payment rejected",
                "Your payment for booking " + payment.getBooking().getBookingRef()
                        + " was rejected.\nReason: " + reason
        );
    }

    private void sendRefundConfirmedEmail(Payment payment) {
        emailService.sendEmail(
                payment.getUser().getEmail(),
                "Refund successful",
                "Your refund request for booking " + payment.getBooking().getBookingRef()
                        + " has been confirmed. Refund amount: "
                        + payment.getRefundAmount() + " " + payment.getBooking().getCurrency()
        );
    }
}
