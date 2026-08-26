package com.example.Driver_Service.controlle;

import com.example.Driver_Service.dto.request.CreateDriverRequest;
import com.example.Driver_Service.dto.request.UpdateDriverRequest;
import com.example.Driver_Service.dto.request.UpdateLocationRequest;
import com.example.Driver_Service.dto.request.UpdateStatusRequest;
import com.example.Driver_Service.dto.response.DriverResponse;
import com.example.Driver_Service.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
@Tag(
        name = "Driver Controller ",
        description = "Driver controller and /drivers/**  'entry point of this driver service '"
)
public class DriverController {

    private final DriverService driverService;
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Driver by ID",
            description = "Getting driver by ID"
    )
    public ResponseEntity<DriverResponse> getDriver (@Parameter(
            name = "passing Id to url"
    ) @PathVariable Long id) {
        DriverResponse driverResponse = driverService.getDriver(id);
        return ResponseEntity.ok(driverResponse);
    }

    @Operation(
            summary = "Create Driver",
            description = "Create driver and /drivers/create"
    )
    @PostMapping("/create")
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

    @PutMapping("/update/status/{id}")
    public ResponseEntity<String> updateDriverStatus( @PathVariable Long id, @RequestBody UpdateStatusRequest updateStatusRequest) {

        driverService.updateStatus(id, updateStatusRequest);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/status")
    public ResponseEntity<List<DriverResponse>> getDriversByStatus(@RequestParam String status ) {
        List<DriverResponse> driverResponses= driverService.getDriversByStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(driverResponses);
    }

    @GetMapping
    public ResponseEntity<Page<DriverResponse>> getAllDriver(@PageableDefault(size = 10,sort = "name",direction = Sort.Direction.DESC) Pageable pageable) {
        return  ResponseEntity.status(HttpStatus.OK).body(driverService.getAllDrivers(pageable));
    }

}
