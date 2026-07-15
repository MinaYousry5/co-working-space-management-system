package com.workspace.booking.service;

import com.workspace.booking.common.ApiResponse;
import com.workspace.booking.dto.LoginRequest;
import com.workspace.booking.dto.RegisterRequest;
import com.workspace.booking.dto.ResetPasswordRequest;

public interface AuthService {

    ApiResponse<?> login(LoginRequest request);

    ApiResponse<Void> forgotPassword(String email);

    ApiResponse<Void> resetPassword(ResetPasswordRequest request);
}
