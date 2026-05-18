package com.rashed.ecommerce.orderservice.order.events;

public record OrderCreatedItemEvent(
        Long productId,
        Integer quantity
) {
}