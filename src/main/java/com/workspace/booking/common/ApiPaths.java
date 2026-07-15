package com.workspace.booking.common;

public class ApiPaths {

    public static final String BASE = "/api/v1";

    public static class Auth {
        public static final String BASE = ApiPaths.BASE + "/auth";
        public static final String LOGIN = "/login";
        public static final String REGISTER = "/register";
        public static final String FORGOT_PASSWORD = "/forgot-password";
        public static final String RESET_PASSWORD = "/reset-password";
    }

    public static class User {
        public static final String BASE = ApiPaths.BASE + "/users";
        public static final String GET_PROFILE = "/me";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete/{id}";
        public static final String UPLOAD_AVATAR = "/{userId}/avatar";
    }
}