package com.workspace.booking.dto.contact;

import com.workspace.booking.common.enums.ContactStatus;

import java.time.LocalDateTime;

public record ContactMessageResponse(
        Long id,
        Long userId,
        String customerName,
        String email,
        String phoneNumber,
        String message,
        ContactStatus status,
        Boolean open,
        LocalDateTime createdOn,
        LocalDateTime updatedOn
) {}
