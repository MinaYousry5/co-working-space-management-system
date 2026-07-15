package com.workspace.booking.dto.review;

public record ReviewSummaryResponse(
        Long totalReviews,
        Double averageRating
) {}
