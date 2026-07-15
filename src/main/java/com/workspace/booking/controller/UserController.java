package com.workspace.booking.controller;

import com.workspace.booking.common.ApiPaths;
import com.workspace.booking.common.ApiResponse;
import com.workspace.booking.common.enums.ErrorCode;
import com.workspace.booking.dto.*;
import com.workspace.booking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(ApiPaths.User.BASE)
@RequiredArgsConstructor
@Tag(name = "User Management APIs")
public class UserController {

    private final UserService userService;

    @PostMapping(ApiPaths.Auth.REGISTER)
    @Operation(summary = "User Registration", description = "Create new user account")
    public ApiResponse<?> register(
            @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @GetMapping(ApiPaths.User.GET_PROFILE)
    @Operation(summary = "Get User Profile", description = "Get current user profile by ID")
    public ApiResponse<?> getProfile(
            @RequestParam Long userId) {
        return userService.getProfile(userId);
    }

    @PutMapping(ApiPaths.User.UPDATE)
    @Operation(summary = "Update User", description = "Update user information")
    public ApiResponse<?> updateUser(
            @RequestParam Long userId,
            @RequestBody RegisterRequest request) {
        return userService.updateUser(userId, request);
    }

    @PostMapping(value=ApiPaths.User.UPLOAD_AVATAR, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> uploadAvatar(@PathVariable Long userId, @RequestParam("file") MultipartFile file) throws IOException {
        return userService.uploadAvatar(userId,file);
    }

    @DeleteMapping(ApiPaths.User.DELETE)
    @Operation(summary = "Delete User", description = "Delete user by ID")
    public ApiResponse<?> deleteUser(
            @PathVariable Long id) {
        return userService.deleteUser(id);
    }
}