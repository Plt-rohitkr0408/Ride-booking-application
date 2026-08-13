package com.example.Driver_Service.dto.response;

import com.example.Driver_Service.enums.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private DriverStatus status;
}
