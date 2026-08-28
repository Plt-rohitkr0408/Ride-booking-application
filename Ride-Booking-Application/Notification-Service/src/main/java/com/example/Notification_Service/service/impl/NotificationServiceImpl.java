package com.example.Notification_Service.service.impl;

import com.example.Notification_Service.dto.request.StartNotificationRequest;
import com.example.Notification_Service.entity.Notifications;
import com.example.Notification_Service.repository.NotificationRepository;
import com.example.Notification_Service.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository  notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public String SendNotification(StartNotificationRequest notificationRequest) {
        Notifications notificationUpdate = notificationRepository.findByRideId(notificationRequest.getRideId());

        if(notificationUpdate != null){
            notificationUpdate.setStatus(notificationRequest.getStatus());
            notificationUpdate.setPaymentStatus(notificationRequest.getPaymentStatus());
            if ("Completed".equalsIgnoreCase(notificationRequest.getStatus())) {
                notificationUpdate.setRideCompletedDate(LocalDateTime.now());
            }
            notificationRepository.save(notificationUpdate);
        }else {
            Notifications notification = new Notifications();
            notification.setRideId(notificationRequest.getRideId());
            notification.setEmail(notificationRequest.getEmail());
            notification.setDriverName(notificationRequest.getDriverName());
            notification.setFare(notificationRequest.getFare());
            notification.setDroppedAddress(notificationRequest.getDroppedAddress());
            notification.setPaymentStatus(notificationRequest.getPaymentStatus());
            notification.setStatus(notificationRequest.getStatus());
            notification.setPrickedAddress(notificationRequest.getPrickedAddress());
            notification.setUsername(notificationRequest.getUsername());
            if (notificationRequest.getStatus().equalsIgnoreCase("Completed")) {
                notification.setRideCompletedDate(LocalDateTime.now());
            } else {
                notification.setRideCreatedDate(LocalDateTime.now());
            }

            notificationRepository.save(notification);
        }

        return "Send Notification";
    }
}








