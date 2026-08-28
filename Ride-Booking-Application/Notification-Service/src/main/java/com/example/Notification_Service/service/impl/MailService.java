package com.example.Notification_Service.service.impl;

import com.example.Notification_Service.dto.request.StartNotificationRequest;
import com.example.Notification_Service.entity.Notifications;
import com.example.Notification_Service.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final NotificationServiceImpl  notificationService;

    private final NotificationRepository notificationRepository;

    private final Logger logger = LoggerFactory.getLogger(MailService.class);

    private  final JavaMailSender mailSender;
    private final Boolean mailEnable;
    private final String mailFrom;

    public MailService(NotificationServiceImpl notificationService, NotificationRepository notificationRepository, JavaMailSender mailSender, @Value("${app.mail.enable}") Boolean mailEnable, @Value("${spring.mail.username}") String sender) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
        this.mailEnable = mailEnable;
        this.mailFrom = sender;
    }

    public void sendMail(StartNotificationRequest  request){

        if (!mailEnable) {
            logger.warn("Mail service is disabled");
            return;
        }

        logger.info("Notification status: {}", request.getStatus());
        SimpleMailMessage message = new SimpleMailMessage();

        Notifications notification = notificationRepository.findByRideId(request.getRideId());
        String username;
        String driverName;
        String pickedAddress;
        String droppedAddress;
        String toEmail;
        Double fare;
        if (notification != null) {
            System.out.println("Notification is null ? " + notification.getEmail() +" "+ notification.getUsername());

            logger.info("Using stored notification data for rideId: {}", notification.getRideId());
            username = notification.getUsername();
            driverName = notification.getDriverName();
            pickedAddress = notification.getPrickedAddress();
            droppedAddress = notification.getDroppedAddress();
            fare = notification.getFare();
            toEmail = notification.getEmail();
        } else {
            logger.info("Using request data for rideId: {}", request.getRideId());
            username = request.getUsername();
            driverName = request.getDriverName();
            pickedAddress = request.getPrickedAddress();
            droppedAddress = request.getDroppedAddress();
            fare = request.getFare();
            toEmail = request.getEmail();
        }


        System.out.println("user name"+username);
        System.out.println("driver name"+driverName);
        System.out.println("picked address"+pickedAddress);
        System.out.println("dropped address"+droppedAddress);
        System.out.println("fare"+fare);
        System.out.println("to"+toEmail);
        System.out.println("status"+request.getStatus() );


        message.setFrom(mailFrom);
        message.setTo(toEmail);

        if ("Completed".equalsIgnoreCase(request.getStatus())) {
            message.setSubject("Congratulations! Your ride has been completed!");
            message.setText("""
        Hello, %s
        
        Congratulations! Your ride has been completed!
        Ride ID: %s
        Driver Name: %s 
        Picked Address: %s 
        Dropped Address: %s 
        Fare: %.2f 
        Payment: %s 
        Thank You!! 
        """.formatted( username, request.getRideId(), driverName, pickedAddress, droppedAddress, fare, request.getPaymentStatus() ));
        }
        else {
            message.setSubject("Thank you for creating a ride");
            message.setText(""" 
                    Hello, %s 
                    Thank you for creating a ride! 
                    Ride ID: %s 
                    Driver Name: %s 
                    Picked Address: %s 
                    Dropped Address: %s 
                    Fare: %.2f 
                    Payment: %s 
                    Thank You!! 
                    """.formatted( username, request.getRideId(), driverName, pickedAddress, droppedAddress, fare, request.getPaymentStatus() )); }
        StartNotificationRequest impl = new StartNotificationRequest();
        impl.setRideId(request.getRideId());
        impl.setDriverName(driverName);
        impl.setUsername(username);
        impl.setDroppedAddress(droppedAddress);
        impl.setPrickedAddress(pickedAddress);
        impl.setFare(fare);
        impl.setStatus(request.getStatus());
        impl.setPaymentStatus(request.getPaymentStatus());
        impl.setEmail(toEmail);

        notificationService.SendNotification(impl);
        try {
            mailSender.send(message);
            logger.info( "Mail successfully sent from {} to {} for rideId: {}", mailFrom, toEmail, request.getRideId() );
        } catch (Exception e) {
            logger.error( "Failed to send mail to {} for rideId: {}", toEmail, request.getRideId(), e );
            throw e;
         }
    }
}
