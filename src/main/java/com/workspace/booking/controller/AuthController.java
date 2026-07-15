package com.workspace.booking.controller;

import com.workspace.booking.common.ApiPaths;
import com.workspace.booking.common.ApiResponse;
import com.workspace.booking.dto.LoginRequest;
import com.workspace.booking.dto.ResetPasswordRequest;
import com.workspace.booking.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.Auth.BASE)
@RequiredArgsConstructor
@Tag(name = "Authentication APIs")
public class AuthController {

    private final AuthService authService;

    @PostMapping(ApiPaths.Auth.LOGIN)
    @Operation(summary = "User Login", description = "Login using username and password")
    public ApiResponse<?> login(@RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }

    @PostMapping(ApiPaths.Auth.FORGOT_PASSWORD)
    @Operation(summary = "Forgot Password", description = "Send password reset token to email")
    public ApiResponse<?> forgotPassword(
            @RequestParam String email) {
        return authService.forgotPassword(email);
    }

    @PostMapping(ApiPaths.Auth.RESET_PASSWORD)
    @Operation(summary = "Reset Password", description = "Reset password using token")
    public ApiResponse<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
        return authService.resetPassword(request);
    }
}