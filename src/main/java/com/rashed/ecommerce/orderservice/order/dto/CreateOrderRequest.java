package com.rashed.ecommerce.orderservice.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotNull(message = "Customer id is required")
        Long customerId,

        @NotEmpty(message = "Order items must not be empty")
        List<@Valid OrderItemRequest> items
) {
}