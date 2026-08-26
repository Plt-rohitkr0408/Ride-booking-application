package com.example.oder_service.service;

import com.example.oder_service.dto.CreateOrderReq;
import com.example.oder_service.dto.OrderResponse;
import com.example.oder_service.dto.UpdateStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {

    public Page<OrderResponse> getAllOrders(Pageable pageable);
    public Page<OrderResponse> getOrderByUserId(Long userId , Pageable pageable);
    public Page<OrderResponse> getOrderByDriverId(Long driverId , Pageable pageable);
    public OrderResponse createOrder(CreateOrderReq request);
    public OrderResponse updateOrderStatus(UpdateStatusRequest request);
    public String deleteOrder(Long orderId);
    public OrderResponse getOrderByRideId(Long rideId);
}
