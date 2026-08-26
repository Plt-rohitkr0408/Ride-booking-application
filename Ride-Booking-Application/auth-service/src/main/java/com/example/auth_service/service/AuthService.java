package com.example.auth_service.service;

import com.example.auth_service.dto.request.LoginRequest;
import com.example.auth_service.dto.request.Register;
import com.example.auth_service.dto.response.LoginResponse;
import com.example.auth_service.dto.response.RegisterResponse;
import com.example.auth_service.dto.response.UserResponse;

public interface AuthService {
    RegisterResponse register(Register register);
//    RegisterResponse registerDriver(RegisterDriver request);
    LoginResponse login(LoginRequest request);
    LoginResponse refreshToken(String refreshToken);
    UserResponse getCurrentUser();
    void logout();
}
