package com.example.Notification_Service.service;

import com.example.Notification_Service.dto.request.StartNotificationRequest;

public interface NotificationService {
    public String SendNotification(StartNotificationRequest notifications);
}
