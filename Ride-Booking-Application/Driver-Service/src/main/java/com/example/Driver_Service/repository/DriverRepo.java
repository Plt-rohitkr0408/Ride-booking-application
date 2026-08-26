package com.example.Driver_Service.repository;

import com.example.Driver_Service.entity.Driver;
import  java.util.List;
import java.util.Optional;

import com.example.Driver_Service.enums.DriverStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepo extends JpaRepository<Driver,Long> {
    Optional<Driver> findByDriverId(Long driverId);

   List<Driver> findByStatus(DriverStatus status );

    boolean existsByDriverId(Long driverId);

    Page<Driver> findAll(Pageable pageable);
}
