package com.example.oder_service.repository;

import java.util.*;

import com.example.oder_service.entity.Order;
import com.example.oder_service.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Page<Order> findByUserId(Long userId , Pageable pageable);
    Page<Order> findByDriverId(Long driverId , Pageable pageable);
    Page<Order> findByOrderStatus(OrderStatus orderStatus , Pageable pageable);
    Optional<Order> findById(Long id);
    Order findByRideId(Long rideId);
    void deleteById(Long id);
    Page<Order> findAll(Pageable pageable);
}
