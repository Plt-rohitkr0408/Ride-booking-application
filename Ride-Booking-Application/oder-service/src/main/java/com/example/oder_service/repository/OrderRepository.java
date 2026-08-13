package com.example.oder_service.repository;

import java.util.*;

import com.example.oder_service.entity.Order;
import com.example.oder_service.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByDriverId(Long driverId);
    List<Order> findByOrderStatus(OrderStatus orderStatus);
    Optional<Order> findById(Long id);
    Order findByRideId(Long rideId);
    void deleteById(Long id);
    List<Order> findAll();
}
