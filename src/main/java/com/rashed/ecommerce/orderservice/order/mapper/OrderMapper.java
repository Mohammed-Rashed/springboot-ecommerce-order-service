package com.rashed.ecommerce.orderservice.order.mapper;

import com.rashed.ecommerce.orderservice.order.dto.OrderItemResponse;
import com.rashed.ecommerce.orderservice.order.dto.OrderResponse;
import com.rashed.ecommerce.orderservice.order.entity.Order;
import com.rashed.ecommerce.orderservice.order.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    public OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems()
                .stream()
                .map(this::toItemResponse)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getStatus(),
                order.getTotalAmount(),
                itemResponses,
                order.getCreatedAt()
        );
    }


    private OrderItemResponse toItemResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getId(),
                item.getProductId(),
                item.getProductName(),
                item.getQuantity(),
                item.getUnitPrice()
        );
    }
}
