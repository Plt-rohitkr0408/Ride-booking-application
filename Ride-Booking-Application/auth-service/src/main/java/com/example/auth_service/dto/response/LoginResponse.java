package com.example.auth_service.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private LocalDateTime expireIn;
    private Long userId;
    private String username;
    private String email;
    private String role;
}
