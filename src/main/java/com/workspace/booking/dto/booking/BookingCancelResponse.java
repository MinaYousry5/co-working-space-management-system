package com.workspace.booking.dto.booking;

import java.util.List;

public record BookingCancelResponse(
        BookingResponse cancelledBooking,
        List<BookingResponse> remainingBookings
) {}
