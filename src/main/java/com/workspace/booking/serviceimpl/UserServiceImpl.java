package com.workspace.booking.serviceimpl;

import com.workspace.booking.common.ApiResponse;
import com.workspace.booking.common.enums.ErrorCode;
import com.workspace.booking.common.exception.CustomException;
import com.workspace.booking.dto.*;
import com.workspace.booking.entity.identity.Role;
import com.workspace.booking.entity.identity.User;
import com.workspace.booking.entity.identity.UserRole;
import com.workspace.booking.mapper.UserMapper;
import com.workspace.booking.repository.RoleRepository;
import com.workspace.booking.repository.UserRepository;
import com.workspace.booking.security.CustomUserDetails;
import com.workspace.booking.security.JwtService;
import com.workspace.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public ApiResponse<?> register(RegisterRequest request) {

        log.info("Register request received | username={} | email={}",
                request.getUsername(), request.getEmail());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(ErrorCode.USERNAME_EXISTS);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_EXISTS);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(String.valueOf(request.getPhone()));
        user.setCompanyName(request.getCompanyName());
        user.setJobTitle(request.getJobTitle());

        // IMPORTANT: default role (recommended)
        Role userRole = roleRepository.findByCode("MEMBER")
                .orElseThrow(() -> new CustomException(ErrorCode.ROLE_NOT_FOUND));

        UserRole ur = new UserRole();
        ur.setUser(user);
        ur.setRole(userRole);

        user.setRoles(Set.of(ur));

        userRepository.save(user);

        log.info("User registered successfully | username={}", user.getUsername());

        return ApiResponse.success(userMapper.toResponse(user));
    }

    @Override
    public ApiResponse<UserResponse> getProfile(Long userId) {

        log.info("Fetching profile for userId: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // Just let the mapper do its job! It already handles avatarBase64, company, etc.
        return ApiResponse.success(userMapper.toResponse(user));
    }

    @Override
    public ApiResponse<?> updateUser(Long userId, RegisterRequest request) {

        log.info("Updating user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(String.valueOf(request.getPhone()));
        user.setCompanyName(request.getCompanyName());
        user.setJobTitle(request.getJobTitle());

        if (request.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);

        log.info("User updated successfully: {}", user.getUsername());

        return ApiResponse.success("User updated successfully");
    }

    @Override
    @Transactional
    public ApiResponse<String> uploadAvatar(Long userId, MultipartFile file) throws IOException {
        if (!file.getContentType().startsWith("image/")) {
            throw new CustomException(ErrorCode.INVALID_FILE_TYPE);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.setAvatarBlob(file.getBytes());
        user.setAvatarMimeType(file.getContentType());
        user.setAvatarFileName(file.getOriginalFilename());

        userRepository.save(user);
        return ApiResponse.success("Avatar uploaded successfully");
    }

    @Override
    public ApiResponse<?> deleteUser(Long id) {

        log.warn("Deleting user with id: {}", id);

        if (!userRepository.existsById(id)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        userRepository.deleteById(id);

        log.info("User deleted successfully: {}", id);

        return ApiResponse.success("User deleted successfully");
    }
}
