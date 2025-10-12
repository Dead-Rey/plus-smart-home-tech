package ru.yandex.practicum.kafka.telemetry.collector.handler.sensor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.collector.handler.KafkaEventProducer;
import ru.yandex.practicum.kafka.telemetry.collector.model.sensor.LightSensorEvent;
import ru.yandex.practicum.kafka.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.kafka.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;


@Service
public class LightSensorEventHandler extends BaseSensorEventHandler<LightSensorAvro> {

    public LightSensorEventHandler(KafkaEventProducer kafkaEventProducer,
                                   @Value("${kafka.topic.sensor}") String topic) {
        super(kafkaEventProducer, topic);
    }

    @Override
    public SensorEventType getMessageType() {
        return SensorEventType.LIGHT_SENSOR_EVENT;
    }

    @Override
    protected LightSensorAvro mapToAvro(SensorEvent sensorEvent) {
        LightSensorEvent event = (LightSensorEvent) sensorEvent;
        return LightSensorAvro.newBuilder()
                .setLinkQuality(event.getLinkQuality())
                .setLuminosity(event.getLuminosity())
                .build();
    }
}