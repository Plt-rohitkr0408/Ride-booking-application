package com.example.Ride_Service.mapper;

import com.example.Ride_Service.dto.response.RideResponse;
import com.example.Ride_Service.entity.RideEntity;


public class RideMapper {
    public static RideResponse toResponse(RideEntity  rideEntity) {
        RideResponse rideResponse = new RideResponse();
        rideResponse.setRideId(rideEntity.getRideId());
        rideResponse.setDriverId(rideEntity.getDriverId());
        rideResponse.setDroppedAddress(rideEntity.getDropedAddress());
        rideResponse.setPickedAddress(rideEntity.getPickedAddress());
        rideResponse.setStatus(rideEntity.getRideStatus());
        rideResponse.setFare(rideEntity.getFare());
        rideResponse.setUserId(rideEntity.getUserId());
        return rideResponse;
    }
}
