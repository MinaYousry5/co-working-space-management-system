package com.workspace.booking.serviceimpl;

import com.workspace.booking.common.enums.BookingStatus;
import com.workspace.booking.common.enums.ErrorCode;
import com.workspace.booking.common.exception.CustomException;
import com.workspace.booking.common.exception.InvalidBookingTimeException;
import com.workspace.booking.dto.review.ReviewCreateRequest;
import com.workspace.booking.dto.review.ReviewRatingCountResponse;
import com.workspace.booking.dto.review.ReviewReplyRequest;
import com.workspace.booking.dto.review.ReviewResponse;
import com.workspace.booking.dto.review.ReviewSummaryResponse;
import com.workspace.booking.dto.review.ReviewUpdateRequest;
import com.workspace.booking.entity.booking.Booking;
import com.workspace.booking.entity.engagement.Review;
import com.workspace.booking.entity.engagement.ReviewReply;
import com.workspace.booking.entity.identity.User;
import com.workspace.booking.mapper.ReviewMapper;
import com.workspace.booking.repository.BookingRepository;
import com.workspace.booking.repository.ReviewReplyRepository;
import com.workspace.booking.repository.ReviewRepository;
import com.workspace.booking.repository.UserRepository;
import com.workspace.booking.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewReplyRepository reviewReplyRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public ReviewResponse create(ReviewCreateRequest request) {
        log.info("Creating review userId={} workspaceId={} bookingId={} rating={}",
                request.userId(), request.workspaceId(), request.bookingId(), request.rating());

        Booking booking = bookingRepository.findById(request.bookingId())
                .orElseThrow(() -> {
                    log.warn("Review create failed because bookingId={} was not found", request.bookingId());
                    return new CustomException(ErrorCode.BOOKING_NOT_FOUND);
                });

        validateReviewEligibility(booking, request.userId(), request.workspaceId());

        if (reviewRepository.existsByBookingId(request.bookingId())) {
            log.warn("Review create rejected because bookingId={} already has a review", request.bookingId());
            throw new InvalidBookingTimeException("This booking already has a review");
        }

        Review review = Review.builder()
                .booking(booking)
                .user(booking.getUser())
                .workspace(booking.getWorkspace())
                .rating(request.rating().doubleValue())
                .title(request.title())
                .body(request.body())
                .isVerified(1)
                .isPublished(1)
                .build();

        Review saved = reviewRepository.save(review);
        log.info("Review created successfully id={} bookingId={}", saved.getId(), booking.getId());
        return toResponse(saved);
    }

    @Override
    @Transactional
    public ReviewResponse updateByUser(Long reviewId, ReviewUpdateRequest request) {
        log.info("Updating review id={} by userId={}", reviewId, request.userId());

        Review review = reviewRepository.findByIdAndUserId(reviewId, request.userId())
                .orElseThrow(() -> {
                    log.warn("Review update failed because reviewId={} was not found for userId={}", reviewId, request.userId());
                    return new CustomException(ErrorCode.REVIEW_NOT_FOUND);
                });

        review.setRating(request.rating().doubleValue());
        review.setTitle(request.title());
        review.setBody(request.body());

        Review saved = reviewRepository.save(review);
        log.info("Review updated successfully id={} userId={}", saved.getId(), request.userId());
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewResponse> getAll(Pageable pageable) {
        log.info("Fetching reviews page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return reviewRepository.findAllByOrderByCreatedOnDesc(pageable).map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewResponse> getByRating(Integer rating, Pageable pageable) {
        validateRating(rating);
        log.info("Fetching reviews by rating={} page={} size={}", rating, pageable.getPageNumber(), pageable.getPageSize());
        return reviewRepository.findByRatingOrderByCreatedOnDesc(rating.doubleValue(), pageable).map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewSummaryResponse getSummary() {
        log.info("Fetching review average and total count");
        Object[] result = reviewRepository.getAverageRatingAndTotal();
        Object[] row = result != null && result.length > 0 && result[0] instanceof Object[] ? (Object[]) result[0] : result;

        Double average = row == null || row[0] == null ? 0.0 : ((Number) row[0]).doubleValue();
        Long total = row == null || row[1] == null ? 0L : ((Number) row[1]).longValue();

        double roundedAverage = BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP).doubleValue();
        return new ReviewSummaryResponse(total, roundedAverage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewRatingCountResponse> getRatingCounts() {
        log.info("Fetching review counts grouped by rating");
        List<ReviewRatingCountResponse> response = new ArrayList<>();

        for (int rating = 1; rating <= 5; rating++) {
            response.add(new ReviewRatingCountResponse(rating, 0L));
        }

        for (Object[] row : reviewRepository.countReviewsByRating()) {
            int rating = ((Number) row[0]).intValue();
            long count = ((Number) row[1]).longValue();
            if (rating >= 1 && rating <= 5) {
                response.set(rating - 1, new ReviewRatingCountResponse(rating, count));
            }
        }

        return response;
    }

    @Override
    @Transactional
    public ReviewResponse respond(Long reviewId, ReviewReplyRequest request) {
        log.info("Admin responderId={} responding to reviewId={}", request.responderId(), reviewId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    log.warn("Review response failed because reviewId={} was not found", reviewId);
                    return new CustomException(ErrorCode.REVIEW_NOT_FOUND);
                });

        User responder = userRepository.findById(request.responderId())
                .orElseThrow(() -> {
                    log.warn("Review response failed because responderId={} was not found", request.responderId());
                    return new CustomException(ErrorCode.USER_NOT_FOUND);
                });

        ReviewReply reply = reviewReplyRepository.findByReviewId(reviewId)
                .orElseGet(() -> ReviewReply.builder()
                        .review(review)
                        .responder(responder)
                        .build());

        reply.setResponder(responder);
        reply.setResponseBody(request.responseBody());
        ReviewReply savedReply = reviewReplyRepository.save(reply);

        log.info("Review response saved successfully reviewId={} responseId={}", reviewId, savedReply.getId());
        return reviewMapper.toResponse(review, savedReply);
    }

    private void validateReviewEligibility(Booking booking, Long userId, Long workspaceId) {
        if (!booking.getUser().getId().equals(userId)) {
            throw new InvalidBookingTimeException("Booking does not belong to this user");
        }
        if (!booking.getWorkspace().getId().equals(workspaceId)) {
            throw new InvalidBookingTimeException("Booking does not belong to this workspace");
        }
        if (booking.getEndDatetime() == null || booking.getEndDatetime().isAfter(LocalDateTime.now())) {
            throw new InvalidBookingTimeException("Booking must be ended before writing a review");
        }
//        if (booking.getCheckedInAt() == null) {
//            throw new InvalidBookingTimeException("User must check in to the workspace before writing a review");
//        }
        if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.NO_SHOW) {
            throw new InvalidBookingTimeException("Cancelled or no-show bookings cannot be reviewed");
        }
    }

    private ReviewResponse toResponse(Review review) {
        ReviewReply reply = reviewReplyRepository.findByReviewId(review.getId()).orElse(null);
        return reviewMapper.toResponse(review, reply);
    }

    private void validateRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new InvalidBookingTimeException("Rating must be between 1 and 5");
        }
    }
}
