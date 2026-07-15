package com.workspace.booking.service;

public interface PasswordService {

    void requestReset(String username);

    void resetPassword(String token, String newPassword);
}
