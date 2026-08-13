package com.example.User_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String name;
    private String email;
    private String phone;
    private Long authId;
    private Long userId;
    private String message;
    private String homeAddress;
    private String officeAddress;
    private String profileImage;
}
