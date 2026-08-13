package com.example.oder_service.dto;

import com.example.oder_service.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UpdateStatusRequest {
    private OrderStatus  orderStatus;
    private String paymentStatus;
}
