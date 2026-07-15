package com.workspace.booking.dto.contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactMessageCreateRequest(
        Long userId,

        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must not exceed 255 characters")
        String customerName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 255, message = "Email must not exceed 255 characters")
        String email,

        @NotBlank(message = "Phone number is required")
        @Size(max = 50, message = "Phone number must not exceed 50 characters")
        String phoneNumber,

        @NotBlank(message = "Message is required")
        @Size(max = 4000, message = "Message must not exceed 4000 characters")
        String message
) {}
