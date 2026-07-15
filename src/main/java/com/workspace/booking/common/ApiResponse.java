package com.workspace.booking.common;

import com.workspace.booking.common.enums.ErrorCode;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
public class ApiResponse<T> {

    private String code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code("200")
                .message("Operation successful")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiResponse<?> error(ErrorCode errorCode) {
        return ApiResponse.builder()
                .code(errorCode.code())
                .message(errorCode.message())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiResponse<?> error(String code, String message) {
        return ApiResponse.builder()
                .code(code)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}