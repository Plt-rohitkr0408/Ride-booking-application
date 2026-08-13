package com.example.oder_service.service;

import com.example.oder_service.dto.CreateOrderRequest;
import com.example.oder_service.dto.OrderResponse;
import com.example.oder_service.dto.UpdateStatusRequest;

import java.util.List;

public interface OrderService {

    public List<OrderResponse> getAllOrders();
    public List<OrderResponse> getOrderByUserId(Long userId);
    public List<OrderResponse> getOrderByDriverId(Long driverId);
    public OrderResponse createOrder(CreateOrderRequest request);
    public OrderResponse updateOrderStatus(Long orderId, UpdateStatusRequest request);
    public String deleteOrder(Long orderId);
}
