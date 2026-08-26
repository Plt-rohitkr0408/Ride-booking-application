package com.example.auth_service.dto.request;

import lombok.Data;

@Data
public class Register {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
}
