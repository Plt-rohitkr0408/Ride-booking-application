package com.example.Ride_Service.config;

import com.example.Ride_Service.dto.request.UpdateDriverStatusRequest;
import com.example.Ride_Service.dto.response.DriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "DRIVER-SERVICE" )
public interface DriverClient {
    @GetMapping("/api/v1/driver/status")
    List<DriverResponse> getAvailableDriver(@RequestParam String status);

    @PutMapping("/api/v1/driver/update/status/{id}")
    void updateStatus(@PathVariable Long id,@RequestBody UpdateDriverStatusRequest status);

}
