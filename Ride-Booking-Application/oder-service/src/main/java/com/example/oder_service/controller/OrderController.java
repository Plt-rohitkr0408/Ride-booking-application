package com.example.oder_service.controller;


import com.example.oder_service.dto.OrderResponse;
import com.example.oder_service.dto.UpdateStatusRequest;
import com.example.oder_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//    @PostMapping
//    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
//        System.out.println("request received ");
//        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
//    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllOrders(@PageableDefault(size=10,sort = "createdAt" ,
                               direction = Sort.Direction.DESC) Pageable pageable){
        return  ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders(pageable));
    }

    @PutMapping("/update")
    public ResponseEntity<OrderResponse> updateOrderStatus( @RequestBody UpdateStatusRequest request){
        System.out.println("is it running");
        System.out.println("request received " + request.getOrderStatus());
        return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderStatus(request));
    }

    @GetMapping("/ride/{rideId}")
    public ResponseEntity<OrderResponse> getOrderByrideId(Long rideId){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderByRideId(rideId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<OrderResponse>> getOrderByUserId(@PathVariable Long userId, @PageableDefault(size = 5, sort = "createdAt" ,
    direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderByUserId(userId,pageable));
    }

}
