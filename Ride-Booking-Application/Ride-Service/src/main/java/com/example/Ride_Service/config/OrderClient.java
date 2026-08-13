package com.example.Ride_Service.config;

import com.example.Ride_Service.dto.request.CreateOrderReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ODER-SERVICE")
public interface OrderClient {
    @PostMapping("/api/v1/orders")
    void createOrder(@RequestBody CreateOrderReq req);
}
