package com.rashed.ecommerce.orderservice.order.controller;


import com.rashed.ecommerce.orderservice.order.dto.CreateOrderRequest;
import com.rashed.ecommerce.orderservice.order.dto.OrderResponse;
import com.rashed.ecommerce.orderservice.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(@RequestBody @Valid CreateOrderRequest request) {
        return orderService.createOrder(request);
    }
    @GetMapping("/customer/{customerId}")
    public List<OrderResponse> getCustomerOrders(@PathVariable Long customerId) {
        return orderService.getCustomerOrders(customerId);
    }
    @GetMapping("/{id}")
    public OrderResponse getCustomerOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
