package com.example.Driver_Service.service;

import com.example.Driver_Service.dto.request.CreateDriverRequest;
import com.example.Driver_Service.dto.request.UpdateDriverRequest;
import com.example.Driver_Service.dto.request.UpdateLocationRequest;
import com.example.Driver_Service.dto.request.UpdateStatusRequest;
import com.example.Driver_Service.dto.response.DriverResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DriverService {
    DriverResponse createDriver(CreateDriverRequest createDriverRequest);
    DriverResponse getDriver(Long authId);
    DriverResponse updateDriver(Long authId, UpdateDriverRequest updateDriverRequest);
    void updateLocation(Long authId, UpdateLocationRequest updateLocationRequest);
    void updateStatus(Long authId, UpdateStatusRequest  updateStatusRequest);
    List<DriverResponse> getDriversByStatus(String status );

    Page<DriverResponse> getAllDrivers(Pageable pageable);
}
