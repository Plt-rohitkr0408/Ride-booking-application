package com.example.Ride_Service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UpdateStatusRequest {
    private Long rideId;
    private String  orderStatus;
    private String paymentStatus;
    private String choice;
}
