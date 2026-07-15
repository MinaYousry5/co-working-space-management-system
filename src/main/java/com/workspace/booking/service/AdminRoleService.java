package com.workspace.booking.service;

import com.workspace.booking.common.ApiResponse;
import com.workspace.booking.dto.UserResponse;

import java.util.List;

public interface AdminRoleService {

    ApiResponse<?> assignRole(Long userId, String roleCode);

    ApiResponse<?> removeRole(Long userId, String roleCode);

    ApiResponse<List<UserResponse>> getUsersByRole(String roleCode);
}
