package com.workspace.booking.controller;

import com.workspace.booking.common.ApiResponse;
import com.workspace.booking.dto.booking.BookingCancelRequest;
import com.workspace.booking.dto.booking.BookingCancelResponse;
import com.workspace.booking.dto.booking.BookingCreateRequest;
import com.workspace.booking.dto.booking.BookingResponse;
import com.workspace.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @Operation(summary = "Create booking", description = "Books a workspace only when no active booking overlaps the requested time.")
    public ResponseEntity<ApiResponse<BookingResponse>> create(@Valid @RequestBody BookingCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(bookingService.create(request)));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel booking", description = "Cancels the whole booking, or only a provided part of the booking time.")
    public ResponseEntity<ApiResponse<BookingCancelResponse>> cancel(
            @PathVariable Long id,
            @Valid @RequestBody BookingCancelRequest request) {
        return ResponseEntity.ok(ApiResponse.success(bookingService.cancel(id, request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID")
    public ResponseEntity<ApiResponse<BookingResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(bookingService.getById(id)));
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get bookings by user")
    public ResponseEntity<ApiResponse<Page<BookingResponse>>> getByUser(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(bookingService.getByUser(userId, pageable)));
    }

    @GetMapping("/availability")
    @Operation(summary = "Check workspace availability")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> availability(
            @RequestParam Long workspaceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDatetime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDatetime) {
        boolean available = bookingService.isWorkspaceAvailable(workspaceId, startDatetime, endDatetime);
        return ResponseEntity.ok(ApiResponse.success(Map.of("available", available)));
    }
}
