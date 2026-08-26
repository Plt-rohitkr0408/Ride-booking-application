package com.example.auth_service.config;

import com.example.auth_service.dto.request.CreateDriverRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "DRIVER-SERVICE" , configuration = FeignConfig.class)
public interface DriverClient {
    @PostMapping("/drivers/create")
    void createDriver(@RequestBody CreateDriverRequest createDriverRequest);
}
