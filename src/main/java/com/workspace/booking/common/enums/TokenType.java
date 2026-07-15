package com.workspace.booking.common.enums;

public enum TokenType {
    BEARER,    // Standard login token (JWT)
    REFRESH,   // Used to get a new BEARER token when it expires
    PWD_RESET, // Used for "Forgot Password" flow
    EMAIL_VERIFY // Used for confirming a new account
}
