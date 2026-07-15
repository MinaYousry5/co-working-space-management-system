package com.workspace.booking.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String token;
}
