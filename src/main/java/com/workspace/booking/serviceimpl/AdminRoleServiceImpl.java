package com.workspace.booking.serviceimpl;

import com.workspace.booking.common.ApiResponse;
import com.workspace.booking.common.enums.ErrorCode;
import com.workspace.booking.common.exception.CustomException;
import com.workspace.booking.dto.UserResponse;
import com.workspace.booking.entity.identity.Role;
import com.workspace.booking.entity.identity.User;
import com.workspace.booking.entity.identity.UserRole;
import com.workspace.booking.repository.RoleRepository;
import com.workspace.booking.repository.UserRepository;
import com.workspace.booking.repository.UserRoleRepository;
import com.workspace.booking.service.AdminRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminRoleServiceImpl implements AdminRoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    public ApiResponse<?> assignRole(Long userId, String roleCode) {

        log.info("Assign role {} to user {}", roleCode, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Role role = roleRepository.findByCode(roleCode)
                .orElseThrow(() -> new CustomException(ErrorCode.ROLE_NOT_FOUND));

        boolean exists = userRoleRepository.existsByUserIdAndRoleId(userId, role.getId());

        if (!exists) {
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            userRoleRepository.save(userRole);
        }

        log.info("Role assigned successfully");

        return ApiResponse.success(null);
    }

    @Override
    @Transactional
    public ApiResponse<Void> removeRole(Long userId, String roleCode) {

        log.info("Remove role {} from user {}", roleCode, userId);

        Role role = roleRepository.findByCode(roleCode)
                .orElseThrow(() -> new CustomException(ErrorCode.ROLE_NOT_FOUND));

        userRoleRepository.deleteByUserIdAndRoleId(userId, role.getId());

        log.info("Role removed successfully");

        return ApiResponse.success(null);
    }

    @Override
    public ApiResponse<List<UserResponse>> getUsersByRole(String roleCode) {

        log.info("Get users by role {}", roleCode);

        List<User> users = userRepository.findAllByRoles_Role_Code(roleCode);

        List<UserResponse> response = users.stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .roles(
                                user.getRoles().stream()
                                        .map(ur -> ur.getRole().getCode())
                                        .toList()
                        )
                        .build()
                )
                .toList();

        return ApiResponse.success(response);
    }
}
