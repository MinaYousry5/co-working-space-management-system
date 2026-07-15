package com.workspace.booking.common.enums;

public enum ErrorCode {

    // USER
    USER_NOT_FOUND("U001", "User not found"),
    USERNAME_EXISTS("U002", "Username already exists"),
    INVALID_PASSWORD("U003", "Invalid password"),
    EMAIL_EXISTS("U004", "Email already exists"),
    INVALID_FILE_TYPE("U005","Invalid file type."),

    // CONTACT
    CONTACT_MESSAGE_NOT_FOUND("C001", "Contact message not found"),

    // BOOKING
    BOOKING_NOT_FOUND("B003", "Booking not found"),

    // REVIEW
    REVIEW_NOT_FOUND("R001", "Review not found"),

    // AUTH
    TOKEN_INVALID("A001", "Invalid or expired token"),
    TOKEN_EXPIRED("A002", "Token expired"),
    UNAUTHORIZED("A003", "Unauthorized access"),
    ROLE_NOT_FOUND("A004", "Role not found"),

    // SYSTEM
    VALIDATION_ERROR("S001", "Validation error"),
    INTERNAL_ERROR("S999", "Internal server error");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
