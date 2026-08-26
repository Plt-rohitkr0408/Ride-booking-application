package com.example.User_service.service;

import com.example.User_service.dto.request.CreateUserRequest;
import com.example.User_service.dto.request.UpdateUserRequest;
import com.example.User_service.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface UserService {
    UserResponse createUser(CreateUserRequest createUserRequest);
    UserResponse updateUser(UpdateUserRequest updateUserRequest , Authentication authentication);
    void deleteUser(Authentication authentication);
    UserResponse getUserProfile(Long userId);
    UserResponse getByEmail(String email);
    Page<UserResponse> getAllUsers(Pageable pageable);
}
