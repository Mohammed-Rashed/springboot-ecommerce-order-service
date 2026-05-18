package com.rashed.ecommerce.orderservice.order.consumer;


import com.rashed.ecommerce.orderservice.order.events.StockReservedEvent;
import com.rashed.ecommerce.orderservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockReservedConsumer {
    final public OrderService orderService;
    @KafkaListener(topics = "${app.kafka.topics.stock-reserved}")
    public void consumeStockReserved(@Payload(required = false) StockReservedEvent event) {
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
