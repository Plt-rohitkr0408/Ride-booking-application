package com.example.Ride_Service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class DriverResponse {
    private Long id;
    private Long  authId;
    private String name;
    private String email;
    private String phone;

    private String vehicleNumber;
    private String vehicleTye;

    private String licence;

    private Double rating;
    private String status;
}
