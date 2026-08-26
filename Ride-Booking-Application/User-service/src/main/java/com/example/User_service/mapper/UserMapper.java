package com.example.User_service.mapper;

import com.example.User_service.dto.response.UserResponse;
import com.example.User_service.entity.User;

public class UserMapper {
    public static UserResponse toUserResponse(User user , String message){
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .userId(user.getUserId())
                .homeAddress(user.getHomeAddress())
                .officeAddress(user.getOfficeAddress())
                .profileImage(user.getProfileImage())
                .message(message)
                .build();
    }
}
