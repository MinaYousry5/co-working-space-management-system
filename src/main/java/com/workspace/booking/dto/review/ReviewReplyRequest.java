package com.workspace.booking.dto.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewReplyRequest(
        @NotNull(message = "Responder ID is required")
        Long responderId,

        @NotBlank(message = "Response body is required")
        @Size(max = 4000, message = "Response body must not exceed 4000 characters")
        String responseBody
) {}
