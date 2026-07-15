package com.workspace.booking.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String companyName;
    private String jobTitle;
}

