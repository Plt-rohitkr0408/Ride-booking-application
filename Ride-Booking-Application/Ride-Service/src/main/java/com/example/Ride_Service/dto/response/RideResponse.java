package com.example.Ride_Service.dto.response;

import com.example.Ride_Service.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor
public class RideResponse {

    private Long rideId;
    private Long userId;
    private Long driverId;
    private String pickedAddress;
    private RideStatus status;
    private String droppedAddress;
    private Double fare;

}
