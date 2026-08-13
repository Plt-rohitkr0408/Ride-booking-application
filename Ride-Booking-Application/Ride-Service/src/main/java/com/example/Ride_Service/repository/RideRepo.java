package com.example.Ride_Service.repository;

import com.example.Ride_Service.entity.RideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepo extends JpaRepository<RideEntity , Long> {

    List<RideEntity> findByUserId(Long userId);
    List<RideEntity> findByDriverId(Long driverId);

}
