package com.example.Ride_Service.controlle;

import com.example.Ride_Service.dto.request.CreateRideRequest;
import com.example.Ride_Service.dto.response.RideResponse;
import com.example.Ride_Service.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ride")
public class RideController {

    private final RideService rideService;
    @Autowired
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<RideResponse> getRideById(@PathVariable Long rideId){
        RideResponse rideResponse = rideService.getRides(rideId);
        return ResponseEntity.ok(rideResponse);
    }


    @PostMapping
    public ResponseEntity<RideResponse> createRide(@RequestBody CreateRideRequest createRideRequest, Authentication authentication){
        RideResponse rideResponse = rideService.createRide(createRideRequest, authentication);
        return ResponseEntity.ok(rideResponse);
    }

    @PutMapping("/{rideId}/accept")
    public ResponseEntity<RideResponse> acceptRide(@PathVariable Long rideId){
        RideResponse rideResponse = rideService.AccceptRide(rideId);
        return ResponseEntity.ok(rideResponse);
    }

    @PutMapping("/{rideId}/cancel")
    public ResponseEntity<RideResponse>  cancelRide(@PathVariable Long rideId){
        RideResponse rideResponse = rideService.CancelledRide(rideId);
        return ResponseEntity.ok(rideResponse);
    }

    @PutMapping("/{rideId}/start")
    public ResponseEntity<RideResponse>  startRide(@PathVariable Long rideId){
        RideResponse rideResponse = rideService.StartRide(rideId);
        return ResponseEntity.ok(rideResponse);
    }

    @PutMapping("/{rideId}/completed")
    public ResponseEntity<RideResponse>  completedRide(@PathVariable Long rideId){
        RideResponse rideResponse = rideService.CompleteRide(rideId);
        return ResponseEntity.ok(rideResponse);
    }


}
