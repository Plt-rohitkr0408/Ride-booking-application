package com.example.auth_service.dto.response;


import lombok.Data;

@Data
public class DriverResponse {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String phone;
}
