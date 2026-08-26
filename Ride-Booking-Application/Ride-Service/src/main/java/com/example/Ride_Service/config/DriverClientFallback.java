package com.example.Ride_Service.config;

import com.example.Ride_Service.dto.request.UpdateDriverStatusRequest;
import com.example.Ride_Service.dto.response.DriverResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DriverClientFallback implements DriverClient{

    private static final Logger logger = LoggerFactory.getLogger(DriverClientFallback.class);
    @Override
    public List<DriverResponse> getDriversByStatus(String status) {
        logger.error("Driver Server is down ");
        return null;
    }

    @Override
    public void updateDriverStatus(Long id, UpdateDriverStatusRequest status) {
        logger.error("Driver Server is down");
    }
}
