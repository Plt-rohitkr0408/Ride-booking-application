package com.example.Notification_Service.service.impl;

import com.example.Notification_Service.dto.request.StartNotificationRequest;
import org.springframework.stereotype.Service;

@Service
public class KafkaListener {
    private final MailService mailService;
    public KafkaListener(MailService mailService) {
        this.mailService = mailService;
    }

    @org.springframework.kafka.annotation.KafkaListener(
            topics = "notification-send",
            groupId = "notifications",
            containerFactory = "notificationKafkaListenerContainerFactory"
    )
    public void NotificationListen(StartNotificationRequest startNotificationRequest){
        mailService.sendMail(startNotificationRequest);
    }
}
