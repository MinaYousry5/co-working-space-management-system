package com.workspace.booking.dto.booking;

import com.workspace.booking.common.enums.BookingStatus;
import com.workspace.booking.common.enums.DurationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingResponse(
        Long id,
        String bookingRef,
        Long userId,
        Long workspaceId,
        String workspaceName,
        LocalDateTime startDatetime,
        LocalDateTime endDatetime,
        DurationType durationType,
        BookingStatus status,
        Integer numAttendees,
        String purpose,
        BigDecimal basePrice,
        BigDecimal discountAmount,
        BigDecimal taxAmount,
        BigDecimal totalAmount,
        String currency,
        LocalDateTime cancelledAt,
        String cancelReason
) {}
