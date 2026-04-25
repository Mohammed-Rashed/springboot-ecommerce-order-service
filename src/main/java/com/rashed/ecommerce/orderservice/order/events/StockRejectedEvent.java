package com.rashed.ecommerce.orderservice.order.events;

public record StockRejectedEvent(
        Long orderId,
        String reason
) {
}
