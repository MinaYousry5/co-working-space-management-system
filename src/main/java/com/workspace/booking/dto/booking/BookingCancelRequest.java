package com.workspace.booking.dto.booking;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BookingCancelRequest(
        @NotNull(message = "Cancelled by user ID is required")
        Long cancelledByUserId,

        String reason,

        LocalDateTime cancelStartDatetime,
        LocalDateTime cancelEndDatetime
) {}
