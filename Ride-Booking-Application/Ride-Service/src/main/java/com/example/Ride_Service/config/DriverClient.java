package com.example.Ride_Service.config;

import com.example.Ride_Service.dto.request.UpdateDriverStatusRequest;
import com.example.Ride_Service.dto.response.DriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "DRIVER-SERVICE" , configuration = RequestInterceptor.class)
public interface DriverClient {
    @GetMapping("/drivers/status")
    List<DriverResponse> getDriversByStatus(@RequestParam String status);

    @PutMapping("/drivers/update/status/{id}")
    void updateDriverStatus(@PathVariable Long id,@RequestBody UpdateDriverStatusRequest status);

}
