package com.example.Ride_Service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
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
