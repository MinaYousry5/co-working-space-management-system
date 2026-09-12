package com.workspace.booking.dto.payment;

import com.workspace.booking.common.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;

public record PaymentStatusUpdateRequest(
        @NotNull(message = "Status is required")
        PaymentStatus status,

        Long adminId,

        String reasonOfReject
) {}
