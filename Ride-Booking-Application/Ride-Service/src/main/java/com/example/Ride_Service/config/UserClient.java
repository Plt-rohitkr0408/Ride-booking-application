package com.example.Ride_Service.config;

import com.example.Ride_Service.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE" ,configuration = RequestInterceptor.class)
public interface UserClient {
    @GetMapping("/users/{authId}")
    UserResponse getUserByAuthId(@PathVariable Long authId);

    @GetMapping("/users/email")
    UserResponse getUserByEmail(@RequestParam("email") String email);
}
