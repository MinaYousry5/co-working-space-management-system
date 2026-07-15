package com.workspace.booking.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
