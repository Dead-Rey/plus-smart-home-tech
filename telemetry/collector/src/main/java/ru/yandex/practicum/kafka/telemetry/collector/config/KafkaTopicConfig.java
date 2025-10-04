package ru.yandex.practicum.kafka.telemetry.collector.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic sensorsTopic() {
        return TopicBuilder.name("telemetry.sensors.v1")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic hubsTopic() {
        return TopicBuilder.name("telemetry.hubs.v1")
                .partitions(3)
                .replicas(1)
                .build();
    }
}