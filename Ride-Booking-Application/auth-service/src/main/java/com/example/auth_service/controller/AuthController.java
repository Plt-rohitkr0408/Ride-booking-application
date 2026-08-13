package com.example.auth_service.controller;

import com.example.auth_service.dto.request.LoginRequest;
import com.example.auth_service.dto.request.RegisterDriver;
import com.example.auth_service.dto.request.RegisterUser;
import com.example.auth_service.dto.response.LoginResponse;
import com.example.auth_service.dto.response.RegisterResponse;
import com.example.auth_service.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService= authService;
    }

    @PostMapping("/user")
    public ResponseEntity<RegisterResponse> createUser( @RequestBody RegisterUser registerUser){
        RegisterResponse registerResponse =authService.registerUser(registerUser);
        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/driver")
    public ResponseEntity<RegisterResponse> createDriver(@RequestBody RegisterDriver registerDriver){
        RegisterResponse registerResponse =authService.registerDriver(registerDriver);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse =authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
