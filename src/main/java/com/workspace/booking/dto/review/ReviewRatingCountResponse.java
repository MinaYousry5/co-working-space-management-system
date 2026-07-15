package com.workspace.booking.dto.review;

public record ReviewRatingCountResponse(
        Integer rating,
        Long totalReviews
) {}
