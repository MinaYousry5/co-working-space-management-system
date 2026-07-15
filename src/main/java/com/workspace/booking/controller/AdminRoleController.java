package com.workspace.booking.controller;

import com.workspace.booking.common.ApiResponse;
import com.workspace.booking.dto.UserResponse;
import com.workspace.booking.service.AdminRoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/roles")
@RequiredArgsConstructor
@Slf4j
public class AdminRoleController {

    private final AdminRoleService adminRoleService;

    @PostMapping("/{userId}/assign")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Assign role to user", description = "Send password reset token to email")
    public ApiResponse<?> assignRole(
            @PathVariable Long userId,
            @RequestBody String roleCode) {

        return adminRoleService.assignRole(userId, roleCode);
    }

    @PostMapping("/{userId}/remove")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Remove role from user", description = "Send password reset token to email")
    public ApiResponse<?> removeRole(
            @PathVariable Long userId,
            @RequestBody String roleCode) {

        return adminRoleService.removeRole(userId, roleCode);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get users by role", description = "Send password reset token to email")
    public ApiResponse<List<UserResponse>> getUsersByRole(
            @RequestParam String role) {

        return adminRoleService.getUsersByRole(role);
    }
}