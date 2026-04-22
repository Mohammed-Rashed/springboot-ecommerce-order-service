package com.rashed.ecommerce.orderservice.order.dto;

import java.util.List;

public record CreateOrderRequest(
        Long customerId,
        List<OrderItemRequest> items
) {
}