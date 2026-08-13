package com.example.Ride_Service.config;

import com.example.Ride_Service.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {
    @GetMapping("/api/v1/user/{authId}")
    UserResponse getUserByAuthId(@PathVariable Long authId);

    @GetMapping("/api/v1/user/email")
    UserResponse getUserByEmail(@RequestParam("email") String email);

}
