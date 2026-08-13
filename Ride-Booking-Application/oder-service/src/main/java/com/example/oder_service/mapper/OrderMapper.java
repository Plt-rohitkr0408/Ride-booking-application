package com.example.oder_service.mapper;

import com.example.oder_service.dto.OrderResponse;
import com.example.oder_service.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    public OrderResponse toOrderResponse(Order order) {
       OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setUserId(order.getUserId());
        orderResponse.setDriverId(order.getDriverId());
        orderResponse.setPrice(order.getFare());
        orderResponse.setOrderStatus(order.getOrderStatus());
        orderResponse.setCreateAt(order.getCreatedAt());
        orderResponse.setStartAt(order.getStartedAt());
        orderResponse.setEndAt(order.getEndedAt());
        orderResponse.setPickupLocation(order.getPickedLocation());
        orderResponse.setDropoffLocation(order.getDroppedLocation());
        orderResponse.setPaymentStatus(order.getPaymentStatus());
        return orderResponse;
    }

    public List<OrderResponse> toOrderResponseList(List<Order> orders) {
        return orders.stream().map(order-> toOrderResponse(order)).toList();
    }
}
