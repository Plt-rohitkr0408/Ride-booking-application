package com.example.oder_service.service.impl;

import com.example.oder_service.dto.CreateOrderRequest;
import com.example.oder_service.dto.OrderResponse;
import com.example.oder_service.dto.UpdateStatusRequest;
import com.example.oder_service.entity.Order;
import com.example.oder_service.enums.OrderStatus;
import com.example.oder_service.mapper.OrderMapper;
import com.example.oder_service.repository.OrderRepository;
import com.example.oder_service.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toOrderResponseList(orders);
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setPaymentStatus(request.getPaymentStatus().toUpperCase());
        order.setFare(request.getFare());
        order.setDriverId(request.getDriverId());
        order.setRideId(request.getRideId());
        order.setPickedLocation(request.getPickedLocation());
        order.setDroppedLocation(request.getDroppedLocation());
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setStartedAt(null);
        order.setEndedAt(null);
        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, UpdateStatusRequest request) {
        return null;
    }

    @Override
    public String deleteOrder(Long orderId) {
        return "";
    }

    @Override
    public List<OrderResponse> getOrderByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<OrderResponse> getOrderByDriverId(Long driverId) {
        return List.of();
    }
}
