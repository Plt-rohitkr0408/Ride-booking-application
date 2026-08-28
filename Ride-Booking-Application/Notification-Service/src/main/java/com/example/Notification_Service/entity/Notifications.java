package com.example.Notification_Service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor @Builder
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long rideId;
    private String prickedAddress;
    private String droppedAddress;
    private String email;
    private String username;
    private String driverName;
    private LocalDateTime rideCreatedDate;
    private LocalDateTime rideCompletedDate;
    private Double fare;
    private String status;



}
