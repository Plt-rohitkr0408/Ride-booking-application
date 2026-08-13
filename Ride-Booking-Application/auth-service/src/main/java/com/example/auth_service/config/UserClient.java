package com.example.auth_service.config;


import com.example.auth_service.dto.request.CreateUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {
    @PostMapping("/api/v1/user")
    void createUser(@RequestBody CreateUserRequest createUserRequest);
}
