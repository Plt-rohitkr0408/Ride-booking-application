package com.example.Notification_Service.repository;

import com.example.Notification_Service.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notifications, Long> {
    Notifications findByRideId(Long rideId);
    boolean existsByRideId(Long rideId);
}
