package com.example.oder_service.dto;

import com.example.oder_service.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private Long driverId;
    private Double price;
    private LocalDateTime createAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String pickupLocation;
    private String dropoffLocation;
    private String paymentStatus;
    private OrderStatus orderStatus;
}
