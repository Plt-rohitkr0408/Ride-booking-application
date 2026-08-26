package com.example.Ride_Service.service;

import com.example.Ride_Service.config.DriverClient;
import com.example.Ride_Service.config.UserClient;
import com.example.Ride_Service.dto.request.CreateOrderReq;
import com.example.Ride_Service.dto.request.CreateRideRequest;
import com.example.Ride_Service.dto.request.UpdateDriverStatusRequest;
import com.example.Ride_Service.dto.request.UpdateStatusRequest;
import com.example.Ride_Service.dto.response.DriverResponse;
import com.example.Ride_Service.dto.response.RideResponse;
import com.example.Ride_Service.dto.response.UserResponse;
import com.example.Ride_Service.entity.RideEntity;
import com.example.Ride_Service.enums.RideStatus;
import com.example.Ride_Service.mapper.RideMapper;
import com.example.Ride_Service.repository.RideRepo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RideServiceImpl implements  RideService {
    private final UserClient userClient;
    private final DriverClient driverClient;
    private final RideRepo rideRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public RideServiceImpl(UserClient userClient, DriverClient driverClient, RideRepo rideRepo, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userClient = userClient;
        this.driverClient = driverClient;
        this.rideRepo = rideRepo;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional
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

        List<DriverResponse> driverResponse = driverClient.getDriversByStatus("online");
        if(driverResponse.isEmpty()){
            throw new IllegalArgumentException("Driver Not Available");
        }
        ride.setDriverId(driverResponse.get(0).getId());
        RideEntity saveRide = rideRepo.save(ride);
        System.out.println("save ride");
        CreateOrderReq createOrderReq = new CreateOrderReq();
        createOrderReq.setDriverId(saveRide.getDriverId());
        createOrderReq.setFare(saveRide.getFare());
        createOrderReq.setPaymentStatus("PENDING");
        createOrderReq.setDroppedLocation(saveRide.getDropedAddress());
        createOrderReq.setUserId(saveRide.getUserId());
        createOrderReq.setPickedLocation(saveRide.getPickedAddress());
        createOrderReq.setRideId(saveRide.getRideId());

        kafkaTemplate.send("create-order",  createOrderReq);

        RideResponse rideResponse = new RideResponse();
        rideResponse.setRideId(saveRide.getRideId());
        rideResponse.setPickedAddress(saveRide.getPickedAddress());
        rideResponse.setUserId(saveRide.getUserId());
        rideResponse.setDriverId(saveRide.getDriverId());
        rideResponse.setStatus(saveRide.getRideStatus());
        rideResponse.setDroppedAddress(saveRide.getDropedAddress());

        System.out.println("Every thing is fine");
        return rideResponse;
    }

    @Override
    public RideResponse getRides(Long rideId) {
        RideEntity ride =rideRepo.findById(rideId).orElseThrow(()-> new IllegalStateException("ride not found"));
        return RideMapper.toResponse(ride);
    }

    @Override
    @Transactional
    public RideResponse AccceptRide(Long rideId) {
        RideEntity ride = rideRepo.findById(rideId).orElseThrow(()-> new IllegalStateException("ride not found"));
        ride.setRideStatus(RideStatus.ACCEPTED);
        UpdateDriverStatusRequest request = new UpdateDriverStatusRequest();
        request.setStatus("BUSY");
        RideEntity saved = rideRepo.save(ride);
        driverClient.updateDriverStatus(ride.getDriverId(),request);

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setRideId(saved.getRideId());
        updateStatusRequest.setOrderStatus("DRIVER_ASSIGNED");
        updateStatusRequest.setChoice("");
        updateStatusRequest.setPaymentStatus("PENDING");

        kafkaTemplate.send("update-order",updateStatusRequest)
                .whenComplete((status, exception)->{
                    if(exception!=null){
                        throw new RuntimeException(" Kafka fail to send");
                    }else{
                        System.out.println("Kafka Send successfully");
                    }
                });
        System.out.println("Update Order Status");
        return RideMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public RideResponse CancelledRide(Long rideId) {
        RideEntity ride=rideRepo.findById(rideId).orElseThrow(()-> new IllegalStateException("ride not found"));
        ride.setRideStatus(RideStatus.CANCELLED);
        RideEntity savedRide= rideRepo.save(ride);
        UpdateDriverStatusRequest request = new UpdateDriverStatusRequest();
        request.setStatus("ONLINE");
        driverClient.updateDriverStatus(ride.getDriverId(), request);

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setRideId(savedRide.getRideId());
        updateStatusRequest.setOrderStatus("CANCELLED");
        updateStatusRequest.setChoice("");
        updateStatusRequest.setPaymentStatus("");

        kafkaTemplate.send("update-order",updateStatusRequest)
                .whenComplete((status, exception)->{
                    if(exception!=null){
                        throw new RuntimeException(" Kafka fail to send");
                    }else
                        {
                        System.out.println("Kafka Send successfully");
                        }
                });
        System.out.println("Update Order Status");
        return RideMapper.toResponse(savedRide);
    }

    @Override
    @Transactional
    public RideResponse StartRide(Long rideId) {
        RideEntity ride = rideRepo.findById(rideId).orElseThrow(()-> new IllegalStateException("ride not found"));
        ride.setRideStatus(RideStatus.STARTED);
        ride.setPickedAt(LocalDateTime.now());
        UpdateDriverStatusRequest request = new UpdateDriverStatusRequest();
        request.setStatus("BUSY");
        driverClient.updateDriverStatus(ride.getDriverId(), request);
        RideEntity savedRide= rideRepo.save(ride);
        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setRideId(savedRide.getRideId());
        updateStatusRequest.setOrderStatus("STARTED");
        updateStatusRequest.setChoice(" ");
        updateStatusRequest.setPaymentStatus(" ");

        kafkaTemplate.send("update-order",updateStatusRequest)
                .whenComplete((status, exception)->{
                    if(exception!=null){
                        throw new RuntimeException(" Kafka fail to send");
                    }else
                    {
                        System.out.println("Kafka Send successfully");
                    }
                });
        System.out.println("Update Order Status");
        return RideMapper.toResponse(savedRide);
    }

    @Override
    @Transactional
    public RideResponse CompleteRide(Long rideId ,String choice) {
        RideEntity ride = rideRepo.findById(rideId).orElseThrow(()-> new IllegalStateException("ride not found"));
        if(ride.getRideStatus() == RideStatus.CANCELLED){
            throw new IllegalStateException(" ride cancelled");
        }
        ride.setDropedAt(LocalDateTime.now());
        ride.setRideStatus(RideStatus.COMPLETED);
        UpdateDriverStatusRequest request = new UpdateDriverStatusRequest();
        request.setStatus("ONLINE");
        driverClient.updateDriverStatus(ride.getDriverId(), request);
        RideEntity savedRide= rideRepo.save(ride);

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setRideId(savedRide.getRideId());
        updateStatusRequest.setOrderStatus("COMPLETED");
        updateStatusRequest.setChoice(choice.toUpperCase());

        kafkaTemplate.send("update-order",updateStatusRequest)
                .whenComplete((status, exception)->{
                    if(exception!=null){
                        throw new RuntimeException(" Kafka fail to send");
                    }else
                    {
                        System.out.println("Kafka Send successfully");
                    }
                });
        System.out.println("Update Order Status");

        return RideMapper.toResponse(savedRide);
    }

}
