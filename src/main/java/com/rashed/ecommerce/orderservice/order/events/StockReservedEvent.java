package com.rashed.ecommerce.orderservice.order.events;

public record StockReservedEvent(
        Long orderId
) {
}
