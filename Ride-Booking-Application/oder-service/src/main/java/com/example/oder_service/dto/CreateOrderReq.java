package com.example.oder_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor @Builder
public class CreateOrderReq {
    private Long userId;
    private Long driverId;
    private Long rideId;
    private String pickedLocation;
    private String droppedLocation;
    private Double fare;
    private String paymentStatus;
}
