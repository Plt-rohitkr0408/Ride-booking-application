package com.example.Driver_Service.controlle;

import com.example.Driver_Service.dto.request.CreateDriverRequest;
import com.example.Driver_Service.dto.request.UpdateDriverRequest;
import com.example.Driver_Service.dto.request.UpdateLocationRequest;
import com.example.Driver_Service.dto.request.UpdateStatusRequest;
import com.example.Driver_Service.dto.response.DriverResponse;
import com.example.Driver_Service.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverController {

    private final DriverService driverService;
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getDriver(@PathVariable Long id) {
        DriverResponse driverResponse = driverService.getDriver(id);
        return ResponseEntity.ok(driverResponse);
    }

    @PostMapping("/internal")
    public ResponseEntity<DriverResponse> createDriver(@RequestBody CreateDriverRequest createDriverRequest) {
        DriverResponse driverResponse = driverService.createDriver(createDriverRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(driverResponse);
    }

    @PutMapping("/update/profile/{id}")
    public ResponseEntity<DriverResponse> updateDriver( @PathVariable Long id, @RequestBody UpdateDriverRequest updateDriverRequest) {
        DriverResponse driverResponse = driverService.updateDriver(id, updateDriverRequest);
        return ResponseEntity.ok(driverResponse);
    }

    @PutMapping("/update/location/{id}")
    public ResponseEntity<String> updateDriverLocation( @PathVariable Long id, @RequestBody UpdateLocationRequest updateLocationRequest) {
        driverService.updateLocation(id, updateLocationRequest);
        return ResponseEntity.ok("success");
    }
    // /api/v1/driver/update/status/{id};
    @PutMapping("/update/status/{id}")
    public ResponseEntity<String> updateDriverStatus( @PathVariable Long id, @RequestBody UpdateStatusRequest updateStatusRequest) {
        System.out.println("running start");
        driverService.updateStatus(id, updateStatusRequest);
        System.out.println("running complete");
        return ResponseEntity.ok("success");
    }

    @GetMapping("/status")
    public ResponseEntity<List<DriverResponse>> getDrivers(@RequestParam String status) {
        List<DriverResponse> driverResponses= driverService.getDrivers(status);
        return ResponseEntity.ok(driverResponses);
    }

}
