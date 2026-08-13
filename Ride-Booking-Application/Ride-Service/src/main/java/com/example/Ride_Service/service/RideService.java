package com.example.Ride_Service.service;

import com.example.Ride_Service.dto.request.CreateRideRequest;
import com.example.Ride_Service.dto.response.RideResponse;
import org.springframework.security.core.Authentication;

public interface RideService {
    RideResponse createRide(CreateRideRequest createRideRequest, Authentication authentication);
    RideResponse getRides(Long rideId);
    RideResponse AccceptRide(Long rideId);
    RideResponse CancelledRide(Long rideId);
    RideResponse StartRide(Long rideId);
    RideResponse CompleteRide(Long rideId);
}
