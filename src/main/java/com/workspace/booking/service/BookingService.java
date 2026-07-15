package com.workspace.booking.service;

import com.workspace.booking.dto.booking.BookingCancelRequest;
import com.workspace.booking.dto.booking.BookingCancelResponse;
import com.workspace.booking.dto.booking.BookingCreateRequest;
import com.workspace.booking.dto.booking.BookingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface BookingService {
    BookingResponse create(BookingCreateRequest request);

    BookingCancelResponse cancel(Long bookingId, BookingCancelRequest request);

    BookingResponse getById(Long id);

    Page<BookingResponse> getByUser(Long userId, Pageable pageable);

    boolean isWorkspaceAvailable(Long workspaceId, LocalDateTime startDatetime, LocalDateTime endDatetime);
}
