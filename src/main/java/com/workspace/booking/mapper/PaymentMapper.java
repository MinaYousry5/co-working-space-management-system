package com.workspace.booking.mapper;

import com.workspace.booking.dto.payment.PaymentResponse;
import com.workspace.booking.entity.finance.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getBooking().getId(),
                payment.getBooking().getBookingRef(),
                payment.getUser().getId(),
                payment.getUser().getEmail(),
                payment.getTransactionType(),
                payment.getStatus(),
                payment.getDepositMethod(),
                payment.getPaidToNumber(),
                payment.getSenderNumber(),
                payment.getReferenceCode(),
                payment.getAmount(),
                payment.getRefundAmount(),
                payment.getScreenshotFilename(),
                payment.getReasonOfReject(),
                payment.getAdminDecisionAt(),
                payment.getCreatedOn()
        );
    }
}
