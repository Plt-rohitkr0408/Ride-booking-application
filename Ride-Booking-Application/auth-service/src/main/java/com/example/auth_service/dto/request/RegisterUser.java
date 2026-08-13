package com.example.auth_service.dto.request;

import lombok.Data;

@Data
public class RegisterUser {
    private String name;
    private String email;
    private String password;
    private String phone;
}
