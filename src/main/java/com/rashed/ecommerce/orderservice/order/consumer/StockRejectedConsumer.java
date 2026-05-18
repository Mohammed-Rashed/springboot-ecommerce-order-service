package com.rashed.ecommerce.orderservice.order.consumer;

import com.rashed.ecommerce.orderservice.order.events.StockRejectedEvent;
import com.rashed.ecommerce.orderservice.order.events.StockReservedEvent;
import com.rashed.ecommerce.orderservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockRejectedConsumer {
    final public OrderService orderService;
    private final JsonMapper jsonMapper;
    @KafkaListener(topics = "${app.kafka.topics.stock-rejected}")
    public void consumeStockRejected(String message) throws Exception {
        StockRejectedEvent event = jsonMapper.readValue(message, StockRejectedEvent.class);
        if (event == null) {
            log.warn("Received empty stock-reserved event payload");
            return;
        }
        orderService.markOrderAsRejected(event.orderId());

        log.info(
                "Received stock-rejected event. orderId={}",
                event.orderId()
        );
    }
}
