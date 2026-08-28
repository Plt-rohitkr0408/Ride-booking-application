package com.example.Ride_Service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StartNotificationRequest {

    private Long rideId;

    private String prickedAddress;

    private String droppedAddress;

    private String email;

    private String username;

    private String driverName;

    private String status;

    private Double fare;

    private String paymentStatus;
}
