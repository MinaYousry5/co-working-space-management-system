package com.workspace.booking.service;

import com.workspace.booking.common.enums.ManualDepositMethod;
import com.workspace.booking.common.enums.PaymentStatus;
import com.workspace.booking.common.enums.PaymentTransactionType;
import com.workspace.booking.dto.payment.PaymentRequest;
import com.workspace.booking.dto.payment.PaymentResponse;
import com.workspace.booking.dto.payment.PaymentStatusUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

public interface PaymentService {

    PaymentResponse submitPayment(
            Long bookingId,
            PaymentRequest request
    ) throws IOException;

    Page<PaymentResponse> getAll(Pageable pageable);

    Page<PaymentResponse> getByStatus(PaymentStatus status, Pageable pageable);

    Page<PaymentResponse> getByTransactionType(PaymentTransactionType transactionType, Pageable pageable);

    PaymentResponse updateStatus(Long id, PaymentStatusUpdateRequest request);

    void cancelExpiredUnpaidBookings();
}
