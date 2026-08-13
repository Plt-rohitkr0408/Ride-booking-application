package com.example.auth_service.dto.request;

import lombok.Data;

@Data
public class RegisterDriver {

    private String name;
    private String email;
    private String password;
    private String phone;
}
