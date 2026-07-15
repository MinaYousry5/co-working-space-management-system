package com.workspace.booking.service;

import com.workspace.booking.common.ApiResponse;
import com.workspace.booking.dto.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    ApiResponse<?> register(RegisterRequest request);

    ApiResponse<?> getProfile(Long userId);

    ApiResponse<?> updateUser(Long userId, RegisterRequest request);

    ApiResponse<?> deleteUser(Long id);

    ApiResponse<?> uploadAvatar(Long userId,MultipartFile file) throws IOException;
}
