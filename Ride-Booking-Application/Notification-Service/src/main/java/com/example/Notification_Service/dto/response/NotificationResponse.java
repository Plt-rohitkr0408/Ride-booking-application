package com.example.Notification_Service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private Long rideId;
    private String prickedAddress;
    private String droppedAddress;
    private String email;
    private String username;
    private String driverName;
    private String status;
}
