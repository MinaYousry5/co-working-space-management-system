package com.workspace.booking.dto.review;

import java.time.LocalDateTime;

public record ReviewReplyResponse(
        Long id,
        Long responderId,
        String responderFullName,
        String responseBody,
        LocalDateTime createdOn,
        LocalDateTime updatedOn
) {}
