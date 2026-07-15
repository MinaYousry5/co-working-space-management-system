package com.workspace.booking.common.exception;

import com.workspace.booking.common.ApiResponse;
import com.workspace.booking.common.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCustom(CustomException ex) {

        log.warn("Business exception occurred: code={}, message={}",
                ex.getErrorCode().code(),
                ex.getMessage()
        );

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ex.getErrorCode()));
    }

    @ExceptionHandler(BookingOverlapException.class)
    public ResponseEntity<ApiResponse<?>> handleBookingOverlap(BookingOverlapException ex) {
        log.warn("Booking overlap rejected: {}", ex.getMessage());
        return ResponseEntity.status(409).body(
                ApiResponse.error("B001", ex.getMessage())
        );
    }

    @ExceptionHandler(InvalidBookingTimeException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidBookingTime(InvalidBookingTimeException ex) {
        log.warn("Invalid booking time: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(
                ApiResponse.error("B002", ex.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        log.warn("Validation error: {}", message);

        return ResponseEntity.badRequest().body(
                ApiResponse.error(ErrorCode.VALIDATION_ERROR.code(), message)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneral(Exception ex) {

        log.error("Unexpected system error", ex);

        return ResponseEntity.internalServerError().body(
                ApiResponse.error(ErrorCode.INTERNAL_ERROR)
        );
    }
}
