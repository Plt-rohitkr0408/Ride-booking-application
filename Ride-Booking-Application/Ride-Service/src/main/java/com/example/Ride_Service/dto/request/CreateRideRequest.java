package com.example.Ride_Service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class CreateRideRequest {
    private Double pickedLatitude;
    private Double pickedLongitude;
    private Double droppedLatitude;
    private Double droppedLongitude;

    private String pickedAddress;
    private String droppedAddress;
}
