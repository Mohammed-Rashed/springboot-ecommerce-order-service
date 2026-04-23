package com.rashed.ecommerce.orderservice.order.service;

import com.rashed.ecommerce.orderservice.order.dto.CreateOrderRequest;
import com.rashed.ecommerce.orderservice.order.dto.OrderItemRequest;
import com.rashed.ecommerce.orderservice.order.dto.OrderResponse;
import com.rashed.ecommerce.orderservice.order.entity.Order;
import com.rashed.ecommerce.orderservice.order.entity.OrderItem;
import com.rashed.ecommerce.orderservice.order.entity.OrderStatus;
import com.rashed.ecommerce.orderservice.order.mapper.OrderMapper;
import com.rashed.ecommerce.orderservice.order.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    public OrderResponse createOrder(CreateOrderRequest request) {
        BigDecimal totalAmount = calculateTotalAmount(request);

        Order order = Order.builder()
                .customerId(request.customerId())
                .status(OrderStatus.PENDING)
                .totalAmount(totalAmount)
                .build();

        for (OrderItemRequest itemRequest : request.items()) {
            OrderItem orderItem = OrderItem.builder()
                    .productId(itemRequest.productId())
                    .productName(itemRequest.productName())
                    .quantity(itemRequest.quantity())
                    .unitPrice(itemRequest.unitPrice())
                    .build();

            order.addItem(orderItem);
        }

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    private BigDecimal calculateTotalAmount(CreateOrderRequest request) {
        return request.items()
                .stream()
                .map(this::calculateItemTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateItemTotal(OrderItemRequest itemRequest) {
        return itemRequest.unitPrice()
                .multiply(BigDecimal.valueOf(itemRequest.quantity()));
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        return orderMapper.toResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getCustomerOrders(Long customerId) {
        return orderRepository.findByCustomerIdOrderByCreatedAtDesc(customerId)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

}
