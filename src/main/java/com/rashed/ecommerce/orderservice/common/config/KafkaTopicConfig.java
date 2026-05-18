package com.rashed.ecommerce.orderservice.common.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableConfigurationProperties(KafkaTopicsProperties.class)
public class KafkaTopicConfig {
    @Bean
    public NewTopic orderCreatedTopic(KafkaTopicsProperties topics) {
        return TopicBuilder.name(topics.orderCreated())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic stockReservedTopic(KafkaTopicsProperties topics) {
        return TopicBuilder.name(topics.stockReserved())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic stockRejectedTopic(KafkaTopicsProperties topics) {
        return TopicBuilder.name(topics.stockRejected())
                .partitions(1)
                .replicas(1)
                .build();
    }

}
