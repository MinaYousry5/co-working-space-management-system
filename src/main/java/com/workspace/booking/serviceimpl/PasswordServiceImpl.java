package com.workspace.booking.serviceimpl;

import com.workspace.booking.common.enums.ErrorCode;
import com.workspace.booking.common.enums.TokenType;
import com.workspace.booking.common.exception.CustomException;
import com.workspace.booking.entity.identity.AccessToken;
import com.workspace.booking.entity.identity.User;
import com.workspace.booking.repository.AccessTokenRepository;
import com.workspace.booking.repository.UserRepository;
import com.workspace.booking.service.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordServiceImpl implements PasswordService {

    private final UserRepository userRepository;
    private final AccessTokenRepository tokenRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void requestReset(String username) {

        log.info("Password reset requested for {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String token = UUID.randomUUID().toString();

        AccessToken accessToken = new AccessToken();
        accessToken.setUser(user);
        accessToken.setTokenHash(token);
        accessToken.setTokenType(TokenType.PWD_RESET);
        accessToken.setExpireAt(LocalDateTime.now().plusMinutes(15));
        accessToken.setRevoked(0L);

        tokenRepo.save(accessToken);

        log.info("Reset token generated: {}", token);

        // TODO: send email (later)
    }

    @Override
    public void resetPassword(String token, String newPassword) {

        AccessToken accessToken = tokenRepo.findByTokenHash(token)
                .orElseThrow(() -> new CustomException(ErrorCode.TOKEN_INVALID));

        if (accessToken.getExpireAt().isBefore(LocalDateTime.now())
                || accessToken.getRevoked() == 1) {
            throw new CustomException(ErrorCode.TOKEN_INVALID);
        }

        User user = accessToken.getUser();
        user.setPasswordHash(passwordEncoder.encode(newPassword));

        accessToken.setRevoked(1L);

        userRepository.save(user);
        tokenRepo.save(accessToken);

        log.info("Password reset success for user {}", user.getUsername());
    }
}
