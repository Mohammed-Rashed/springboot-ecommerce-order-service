package com.rashed.ecommerce.orderservice.order.events;

import java.util.List;

public record OrderCreatedEvent(
        Long orderId,
        Long customerId,
        List<OrderCreatedItemEvent> items
) {
}