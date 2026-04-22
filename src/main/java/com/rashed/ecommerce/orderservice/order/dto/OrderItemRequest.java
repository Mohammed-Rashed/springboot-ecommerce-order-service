package com.rashed.ecommerce.orderservice.order.dto;

import java.math.BigDecimal;

public record OrderItemRequest(
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice
) {
}