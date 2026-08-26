package com.example.oder_service.service.impl;

import com.example.oder_service.dto.CreateOrderReq;
import com.example.oder_service.dto.UpdateStatusRequest;
import com.example.oder_service.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaOrderCreate {
    private final OrderService orderService;

    public KafkaOrderCreate(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(
            topics = "create-order",
            groupId = "create-group",
            containerFactory = "createOrderKafkaListenerContainerFactory"
    )
    public void createOrder(CreateOrderReq req){
        System.out.println("Order called");
        orderService.createOrder(req);
        System.out.println("Order Created");
    }

    @KafkaListener(
            topics = "update-order" ,
            groupId = "update-order",
            containerFactory = "updateStatusRequestKafkaListenerContainerFactory"
    )
    public void updateOrder(UpdateStatusRequest request){
        System.out.println("Order updated");
        orderService.updateOrderStatus(request);
        System.out.println("reuest " + request.getOrderStatus());
    }
}
