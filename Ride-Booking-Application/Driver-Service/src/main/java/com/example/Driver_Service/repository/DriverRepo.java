package com.example.Driver_Service.repository;

import com.example.Driver_Service.entity.Driver;
import  java.util.List;

import com.example.Driver_Service.enums.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepo extends JpaRepository<Driver,Long> {
    Driver findByAuthId(Long authId);

    List<Driver> findByStatus(DriverStatus status);

    boolean existsByAuthId(Long authId);
}
