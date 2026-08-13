package com.example.Ride_Service.service;

import com.example.Ride_Service.config.DriverClient;
import com.example.Ride_Service.config.OrderClient;
import com.example.Ride_Service.config.UserClient;
import com.example.Ride_Service.dto.request.CreateOrderReq;
import com.example.Ride_Service.dto.request.CreateRideRequest;
import com.example.Ride_Service.dto.request.UpdateDriverStatusRequest;
import com.example.Ride_Service.dto.response.DriverResponse;
import com.example.Ride_Service.dto.response.RideResponse;
import com.example.Ride_Service.dto.response.UserResponse;
import com.example.Ride_Service.entity.RideEntity;
import com.example.Ride_Service.enums.RideStatus;
import com.example.Ride_Service.mapper.RideMapper;
import com.example.Ride_Service.repository.RideRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RideServiceImpl implements  RideService {
    private final UserClient userClient;
    private final DriverClient driverClient;
    private final RideRepo rideRepo;
    private final OrderClient   orderClient;

    public RideServiceImpl(UserClient userClient, DriverClient driverClient, RideRepo rideRepo, OrderClient orderClient) {
        this.userClient = userClient;
        this.driverClient = driverClient;
        this.rideRepo = rideRepo;
        this.orderClient = orderClient;
    }

    @Override
    public RideResponse createRide(CreateRideRequest createRideRequest, Authentication authentication) {
        String email= authentication.getName();

        UserResponse userResponse = userClient.getUserByEmail(email);

        RideEntity ride = new RideEntity();
        ride.setUserId(userResponse.getUserId());
        ride.setPickedLatitude(createRideRequest.getPickedLatitude());
        ride.setPickedLongitude(createRideRequest.getPickedLongitude());
        ride.setDropedLatitude(createRideRequest.getDroppedLatitude());
        ride.setDropedLongitude(createRideRequest.getDroppedLongitude());
        ride.setPickedAddress(createRideRequest.getPickedAddress());
        ride.setDropedAddress(createRideRequest.getDroppedAddress());
        ride.setRideStatus(RideStatus.REQUESTED);
        ride.setBookedAt(LocalDateTime.now());

        List<DriverResponse> driverResponse = driverClient.getAvailableDriver("online");
        if(driverResponse.isEmpty()){
            throw new IllegalArgumentException("Driver Not Available");
        }
        ride.setDriverId(driverResponse.get(0).getAuthId());

        RideEntity saveRide = rideRepo.save(ride);

        System.out.println("Save Ride Successfully");

        CreateOrderReq orderReq = new CreateOrderReq();
        orderReq.setDriverId(saveRide.getDriverId());
        orderReq.setUserId(saveRide.getUserId());
        orderReq.setRideId(saveRide.getRideId());
        orderReq.setDroppedLocation(saveRide.getDropedAddress());
        orderReq.setPickedLocation(saveRide.getPickedAddress());
        orderReq.setFare(saveRide.getFare());
        orderReq.setPaymentStatus("PENDING");
        System.out.println("Order Created Successfully");
        orderClient.createOrder(orderReq);
        System.out.println("orderclient run Successfully");

        RideResponse rideResponse = new RideResponse();
        rideResponse.setRideId(saveRide.getRideId());
        rideResponse.setPickedAddress(saveRide.getPickedAddress());
        rideResponse.setUserId(saveRide.getUserId());
        rideResponse.setDriverId(driverResponse.get(0).getAuthId());
        rideResponse.setStatus(saveRide.getRideStatus());
        rideResponse.setDroppedAddress(saveRide.getDropedAddress());
        return rideResponse;
    }

    @Override
    public RideResponse getRides(Long rideId) {
        RideEntity ride =rideRepo.findById(rideId).orElseThrow(()-> new IllegalStateException("ride not found"));
        return RideMapper.toResponse(ride);
    }

    @Override
    public RideResponse AccceptRide(Long rideId) {
//        if(SecurityContextHolder.getContext().getAuthentication().getName())
        RideEntity ride = rideRepo.findById(rideId).orElseThrow(()-> new IllegalStateException("ride not found"));
        ride.setRideStatus(RideStatus.ACCEPTED);
        UpdateDriverStatusRequest request = new UpdateDriverStatusRequest();
        request.setStatus("BUSY");
        System.out.println(request);
        driverClient.updateStatus(ride.getDriverId(), request);
        RideEntity saved = rideRepo.save(ride);
        return RideMapper.toResponse(saved);
    }

    @Override
    public RideResponse CancelledRide(Long rideId) {
        RideEntity ride=rideRepo.findById(rideId).orElseThrow(()-> new IllegalStateException("ride not found"));
        ride.setRideStatus(RideStatus.CANCELLED);
        RideEntity savedRide= rideRepo.save(ride);
        UpdateDriverStatusRequest request = new UpdateDriverStatusRequest();
        request.setStatus("Online");
        driverClient.updateStatus(ride.getDriverId(), request);
        return RideMapper.toResponse(savedRide);
    }

    @Override
    public RideResponse StartRide(Long rideId) {
        RideEntity ride = rideRepo.findById(rideId).orElseThrow(()-> new IllegalStateException("ride not found"));
        ride.setRideStatus(RideStatus.STARTED);
        ride.setPickedAt(LocalDateTime.now());
        UpdateDriverStatusRequest request = new UpdateDriverStatusRequest();
        request.setStatus("BUSY");
        driverClient.updateStatus(ride.getDriverId(), request);
        RideEntity savedRide= rideRepo.save(ride);
        return RideMapper.toResponse(savedRide);
    }

    @Override
    public RideResponse CompleteRide(Long rideId) {
        RideEntity ride = rideRepo.findById(rideId).orElseThrow(()-> new IllegalStateException("ride not found"));
        if(ride.getRideStatus() == RideStatus.CANCELLED){
            throw new IllegalStateException(" ride cancelled");
        }
        ride.setDropedAt(LocalDateTime.now());
        ride.setRideStatus(RideStatus.COMPLETED);
        UpdateDriverStatusRequest request = new UpdateDriverStatusRequest();
        request.setStatus("ONLINE");
        driverClient.updateStatus(ride.getDriverId(), request);
        RideEntity savedRide= rideRepo.save(ride);
        return RideMapper.toResponse(savedRide);
    }

}
