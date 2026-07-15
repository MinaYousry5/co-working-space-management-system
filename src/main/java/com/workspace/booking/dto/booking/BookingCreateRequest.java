package com.workspace.booking.dto.booking;

import com.workspace.booking.common.enums.DurationType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BookingCreateRequest(
        @NotNull(message = "User ID is required")
        Long userId,

        @NotNull(message = "Workspace ID is required")
        Long workspaceId,

        Long promoCodeId,

        @NotNull(message = "Start date/time is required")
        @Future(message = "Start date/time must be in the future")
        LocalDateTime startDatetime,

        @NotNull(message = "End date/time is required")
        LocalDateTime endDatetime,

        @NotNull(message = "Duration type is required")
        DurationType durationType,

        @Min(value = 1, message = "Number of attendees must be at least 1")
        Integer numAttendees,

        String purpose,
        String internalNotes
) {}
