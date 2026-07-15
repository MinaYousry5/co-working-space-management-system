package com.workspace.booking.mapper;

import com.workspace.booking.dto.booking.BookingResponse;
import com.workspace.booking.entity.booking.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getBookingRef(),
                booking.getUser() == null ? null : booking.getUser().getId(),
                booking.getWorkspace() == null ? null : booking.getWorkspace().getId(),
                booking.getWorkspace() == null ? null : booking.getWorkspace().getWorkspaceName(),
                booking.getStartDatetime(),
                booking.getEndDatetime(),
                booking.getDurationType(),
                booking.getStatus(),
                booking.getNumAttendees(),
                booking.getPurpose(),
                booking.getBasePrice(),
                booking.getDiscountAmount(),
                booking.getTaxAmount(),
                booking.getTotalAmount(),
                booking.getCurrency(),
                booking.getCancelledAt(),
                booking.getCancelReason()
        );
    }
}
