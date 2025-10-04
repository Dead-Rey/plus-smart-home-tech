package ru.yandex.practicum.kafka.telemetry.collector.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.collector.mapper.HubEventMapper;
import ru.yandex.practicum.kafka.telemetry.collector.mapper.SensorEventMapper;
import ru.yandex.practicum.kafka.telemetry.collector.model.BaseHubEvent;
import ru.yandex.practicum.kafka.telemetry.collector.model.BaseSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventProcessingService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SensorEventMapper sensorEventMapper;
    private final HubEventMapper hubEventMapper;

    private static final String SENSORS_TOPIC = "telemetry.sensors.v1";
    private static final String HUBS_TOPIC = "telemetry.hubs.v1";

    @Async
    public void processSensorEvent(BaseSensorEvent event) {
        try {
            log.info("Processing sensor event: type={}, id={}, hubId={}",
                    event.getType(), event.getId(), event.getHubId());

            // Конвертируем в Avro и отправляем
            SensorEventAvro avroEvent = sensorEventMapper.toAvro(event);
            CompletableFuture<SendResult<String, Object>> future =
                    kafkaTemplate.send(SENSORS_TOPIC, event.getId(), avroEvent);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("✅ Successfully sent sensor event to {}. Key: {}", SENSORS_TOPIC, event.getId());
                } else {
                    log.error("❌ Failed to send sensor event to {}. Key: {}", SENSORS_TOPIC, event.getId(), ex);
                }
            });
        } catch (Exception e) {
            log.error("❌ Error processing sensor event. HubId: {}, SensorId: {}",
                    event.getHubId(), event.getId(), e);
        }
    }

    @Async
    public void processHubEvent(BaseHubEvent event) {
        try {
            log.info("Processing hub event: type={}, hubId={}", event.getType(), event.getHubId());

            // Конвертируем в Avro и отправляем
            HubEventAvro avroEvent = hubEventMapper.toAvro(event);
            CompletableFuture<SendResult<String, Object>> future =
                    kafkaTemplate.send(HUBS_TOPIC, event.getHubId(), avroEvent);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("✅ Successfully sent hub event to {}. Key: {}", HUBS_TOPIC, event.getHubId());
                } else {
                    log.error("❌ Failed to send hub event to {}. Key: {}", HUBS_TOPIC, event.getHubId(), ex);
                }
            });
        } catch (Exception e) {
            log.error("❌ Error processing hub event. HubId: {}, Type: {}",
                    event.getHubId(), event.getType(), e);
        }
    }
}