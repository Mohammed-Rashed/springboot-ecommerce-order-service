package com.rashed.ecommerce.orderservice.order.service;

import com.rashed.ecommerce.orderservice.common.config.KafkaTopicsProperties;
import com.rashed.ecommerce.orderservice.order.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor

public class OrderEventPublisher {
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private final KafkaTopicsProperties kafkaTopicsProperties;
    public void publishOrderCreated(OrderCreatedEvent event) {
        String key = String.valueOf(event.orderId());
        String topic= kafkaTopicsProperties.orderCreated();
        kafkaTemplate.send(topic, key, event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error(
                                "Failed to publish order-created event for orderId={} to topic={}",
                                event.orderId(),
                                topic,
                                exception
                        );
                        return;
                    }

                    log.info(
                            "Published order-created event for orderId={} to topic={} partition={} offset={}",
                            event.orderId(),
                            topic,
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset()
                    );
                });

    }

}
