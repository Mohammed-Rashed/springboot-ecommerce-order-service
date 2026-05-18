package com.rashed.ecommerce.orderservice.order.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
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
public class StockReservedConsumer {
    final public OrderService orderService;
    private final JsonMapper jsonMapper;
    @KafkaListener(topics = "${app.kafka.topics.stock-reserved}")
    public void consumeStockReserved(String message) throws Exception {
        StockReservedEvent event = jsonMapper.readValue(message, StockReservedEvent.class);
        if (event == null) {
            log.warn("Received empty stock-reserved event payload");
            return;
        }
        orderService.markOrderAsConfirmed(event.orderId());

        log.info(
                "Received stock-reserved event. orderId={}",
                event.orderId()
        );
    }
}
