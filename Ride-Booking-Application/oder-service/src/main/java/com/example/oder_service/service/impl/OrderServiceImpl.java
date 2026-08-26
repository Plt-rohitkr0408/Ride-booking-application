package com.example.oder_service.service.impl;

import com.example.oder_service.dto.CreateOrderReq;
import com.example.oder_service.dto.OrderResponse;
import com.example.oder_service.dto.UpdateStatusRequest;
import com.example.oder_service.entity.Order;
import com.example.oder_service.enums.OrderStatus;
import com.example.oder_service.enums.PaymentChoice;
import com.example.oder_service.mapper.OrderMapper;
import com.example.oder_service.repository.OrderRepository;
import com.example.oder_service.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
       Page<Order> orders = orderRepository.findAll(pageable);
       if(orders.isEmpty()){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found");
       }
       return orders.map(orderMapper::toOrderResponse);
    }

    @Override
    public OrderResponse createOrder(CreateOrderReq request) {
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
    public OrderResponse updateOrderStatus(UpdateStatusRequest request) {

        System.out.println(request.getChoice().toString());
        Order order = orderRepository.findByRideId(request.getRideId());
        order.setOrderStatus(OrderStatus.valueOf(request.getOrderStatus().toUpperCase()));

       if(request.getOrderStatus().equalsIgnoreCase("STARTED")){
           order.setStartedAt(LocalDateTime.now());
       }else if(request.getOrderStatus().equalsIgnoreCase("COMPLETED")){
           order.setEndedAt(LocalDateTime.now());
           if(request.getChoice().equalsIgnoreCase(PaymentChoice.CASH.toString())){
               order.setPaymentStatus(request.getChoice().toUpperCase());
           }else if(request.getChoice().equalsIgnoreCase(PaymentChoice.ONLINE.toString())){
               System.out.println("Processing");
               try{
                   Thread.sleep(5000);
                   order.setPaymentStatus(request.getChoice().toUpperCase());
               }catch (InterruptedException e){
                   System.out.println("Interrupted");
               }

           }
       }

        Order savedOrder = orderRepository.save(order);
        OrderResponse orderResponse = orderMapper.toOrderResponse(savedOrder);
        return orderResponse;
    }

    @Override
    public String deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
        return "Order {orderId} has been deleted ";
    }

    @Override
    public OrderResponse getOrderByRideId(Long rideId) {
        Order order = orderRepository.findById(rideId).orElseThrow(()-> new RuntimeException("ride not found"));
        return  orderMapper.toOrderResponse(order);
    }

    @Override
    public Page<OrderResponse> getOrderByUserId(Long userId , Pageable pageable) {
        Page<Order> orders = orderRepository.findByUserId(userId,pageable);
        if(orders.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found");
        }

        return orders.map(
                order -> orderMapper.toOrderResponse(order)
        );
    }

    @Override
    public Page<OrderResponse> getOrderByDriverId(Long driverId , Pageable pageable) {
        Page<Order> orders = orderRepository.findByDriverId(driverId , pageable);

        return  orders.map(
                order -> orderMapper.toOrderResponse(order)
        );
    }
}
