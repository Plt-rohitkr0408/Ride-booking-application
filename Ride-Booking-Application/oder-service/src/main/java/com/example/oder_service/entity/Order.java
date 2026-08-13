package com.example.oder_service.entity;

import com.example.oder_service.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Long userId;
    private Long driverId;
    private Long rideId;
    private Double fare;
    private String paymentStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String pickedLocation;
    private String droppedLocation;

    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

}
