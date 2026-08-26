package com.example.Driver_Service.service;

import com.example.Driver_Service.dto.request.CreateDriverRequest;
import com.example.Driver_Service.dto.request.UpdateDriverRequest;
import com.example.Driver_Service.dto.request.UpdateLocationRequest;
import com.example.Driver_Service.dto.request.UpdateStatusRequest;
import com.example.Driver_Service.dto.response.DriverResponse;
import com.example.Driver_Service.entity.Driver;
import com.example.Driver_Service.enums.DriverStatus;
import com.example.Driver_Service.repository.DriverRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepo  driverRepo;
    public DriverServiceImpl(DriverRepo driverRepo){
        this.driverRepo = driverRepo;
    }

    @Override
    public DriverResponse createDriver(CreateDriverRequest createDriverRequest) {

        Driver driver = new Driver();
        driver.setName(createDriverRequest.getName());
        driver.setAuthId(createDriverRequest.getAuthId());
        driver.setEmail(createDriverRequest.getEmail());
        driver.setPhone(createDriverRequest.getPhone());
        driver.setStatus(DriverStatus.ONLINE);
        driver.setPassword(createDriverRequest.getPassword());
        Driver saveDriver = driverRepo.save(driver);
        DriverResponse driverResponse = DriverResponse.builder()
                .id(saveDriver.getDriverId())
                .email(saveDriver.getEmail())
                .phone(saveDriver.getPhone())
                .authId(saveDriver.getAuthId())
                .name(saveDriver.getName())
                .status(saveDriver.getStatus())
                .build();
        return driverResponse;
    }


    @Override
    public DriverResponse getDriver(Long driverId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(driverRepo.existsById(driverId)){
            throw new RuntimeException("auth not found");
        }
        Driver driver = driverRepo.findById(driverId).orElseThrow(
                ()-> new RuntimeException("driver not found")
        );

        System.out.println(driver);
        DriverResponse driverResponse = DriverResponse.builder()
                .email(driver.getEmail())
                .id(driver.getDriverId())
                .phone(driver.getPhone())
                .authId(driver.getAuthId())
                .name(driver.getName())
                .licence(driver.getLicence())
                .vehicleNumber(driver.getVehicleNumber())
                .status(driver.getStatus())
                .vehicleTye(driver.getVehicleTye())
                .build();
        return driverResponse;
    }

    @Override
    public DriverResponse updateDriver(Long driverId, UpdateDriverRequest updateDriverRequest) {
        Driver driver = driverRepo.findById(driverId).orElseThrow(
                ()-> new RuntimeException("driver not found")
        );
        driver.setLicence(updateDriverRequest.getLicence());
        driver.setVehicleNumber(updateDriverRequest.getVehicleNumber());
        driver.setVehicleTye(updateDriverRequest.getVehicleTye());
        Driver  saveDriver = driverRepo.save(driver);

        DriverResponse driverResponse = DriverResponse.builder()
                .email(saveDriver.getEmail())
                .id(saveDriver.getDriverId())
                .phone(saveDriver.getPhone())
                .authId(saveDriver.getAuthId())
                .name(saveDriver.getName())
                .licence(saveDriver.getLicence())
                .vehicleNumber(saveDriver.getVehicleNumber())
                .status(saveDriver.getStatus())
                .vehicleTye(saveDriver.getVehicleTye())
                .build();
        return driverResponse;
    }

    @Override
    public void updateLocation(Long driverId, UpdateLocationRequest updateLocationRequest) {
        if(!driverRepo.existsById(driverId)){
            throw new RuntimeException("auth not found");
        }

        Driver driver = driverRepo.findById(driverId).orElseThrow(
                ()-> new RuntimeException("driver not found")
        );
        driver.setLatitude(updateLocationRequest.getLatitude());
        driver.setLongitude(updateLocationRequest.getLongitude());
        Driver  saveDriver = driverRepo.save(driver);
    }

    @Override
    public void updateStatus(Long driverId, UpdateStatusRequest updateStatusRequest) {
        Driver driver = driverRepo.findById(driverId).orElseThrow(
                ()-> new RuntimeException("driver not found")
        );
        driver.setStatus(DriverStatus.valueOf(updateStatusRequest.getStatus().toUpperCase()));
        Driver saveDriver = driverRepo.save(driver);
    }


    @Override
    public Page<DriverResponse> getAllDrivers(Pageable pageable) {
        return driverRepo.findAll(pageable).map(m -> DriverResponse.builder()
                .status(m.getStatus()).vehicleTye(m.getVehicleTye())
                .licence(m.getLicence()).vehicleNumber(m.getVehicleNumber())
                .authId(m.getAuthId()).email(m.getEmail())
                .id(m.getDriverId())
                .name(m.getName()).phone(m.getPhone()).build()
        );
    }


    @Override
    public List<DriverResponse> getDriversByStatus(String status) {
       List<Driver> drivers = driverRepo.findByStatus(DriverStatus.valueOf(status.toUpperCase()));
       List<DriverResponse> driverResponses = drivers.stream().map(driver-> DriverResponse.builder()
               .id(driver.getDriverId())
               .licence(driver.getLicence())
               .email(driver.getEmail())
               .status(driver.getStatus())
               .vehicleNumber(driver.getVehicleNumber())
               .vehicleTye(driver.getVehicleTye())
               .authId(driver.getAuthId())
               .phone(driver.getPhone())
               .name(driver.getName())
               .rating(driver.getRating())
               .build()
       ).toList();
       return driverResponses;
    }
}
