package com.workspace.booking.mapper;

import com.workspace.booking.dto.review.ReviewReplyResponse;
import com.workspace.booking.dto.review.ReviewResponse;
import com.workspace.booking.entity.engagement.Review;
import com.workspace.booking.entity.engagement.ReviewReply;
import com.workspace.booking.entity.identity.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewResponse toResponse(Review review, ReviewReply reply) {
        return new ReviewResponse(
                review.getId(),
                review.getUser() == null ? null : review.getUser().getId(),
                fullName(review.getUser()),
                review.getWorkspace() == null ? null : review.getWorkspace().getId(),
                review.getWorkspace() == null ? null : review.getWorkspace().getWorkspaceName(),
                review.getBooking() == null ? null : review.getBooking().getId(),
                review.getRating() == null ? null : review.getRating().intValue(),
                review.getTitle(),
                review.getBody(),
                review.getIsVerified(),
                review.getIsPublished(),
                reply == null ? null : toReplyResponse(reply),
                review.getCreatedOn(),
                review.getUpdatedOn()
        );
    }

    public ReviewReplyResponse toReplyResponse(ReviewReply reply) {
        return new ReviewReplyResponse(
                reply.getId(),
                reply.getResponder() == null ? null : reply.getResponder().getId(),
                fullName(reply.getResponder()),
                reply.getResponseBody(),
                reply.getCreatedOn(),
                reply.getUpdatedOn()
        );
    }

    private String fullName(User user) {
        if (user == null) {
            return null;
        }
        String firstName = user.getFirstName() == null ? "" : user.getFirstName();
        String lastName = user.getLastName() == null ? "" : user.getLastName();
        String fullName = (firstName + " " + lastName).trim();
        return fullName.isBlank() ? user.getUsername() : fullName;
    }
}
