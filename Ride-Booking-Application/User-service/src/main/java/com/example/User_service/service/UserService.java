package com.example.User_service.service;

import com.example.User_service.dto.request.CreateUserRequest;
import com.example.User_service.dto.request.UpdateUserRequest;
import com.example.User_service.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(CreateUserRequest createUserRequest);
    UserResponse updateUser(UpdateUserRequest updateUserRequest);
    void deleteUser(Long authId);
    UserResponse getUserProfile(Long authId);
    UserResponse getByEmail(String email);

}
