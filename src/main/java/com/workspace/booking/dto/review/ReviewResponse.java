package com.workspace.booking.dto.review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long userId,
        String userFullName,
        Long workspaceId,
        String workspaceName,
        Long bookingId,
        Integer rating,
        String title,
        String body,
        Integer isVerified,
        Integer isPublished,
        ReviewReplyResponse adminResponse,
        LocalDateTime createdOn,
        LocalDateTime updatedOn
) {}
