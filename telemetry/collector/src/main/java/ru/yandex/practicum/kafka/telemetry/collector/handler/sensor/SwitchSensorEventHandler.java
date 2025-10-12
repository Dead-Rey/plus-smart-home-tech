package ru.yandex.practicum.kafka.telemetry.collector.handler.sensor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.collector.handler.KafkaEventProducer;
import ru.yandex.practicum.kafka.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.kafka.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.kafka.telemetry.collector.model.sensor.SwitchSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;


@Service
public class SwitchSensorEventHandler extends BaseSensorEventHandler<SwitchSensorAvro> {

    public SwitchSensorEventHandler(KafkaEventProducer kafkaEventProducer,
                                    @Value("${kafka.topic.sensor}") String topic) {
        super(kafkaEventProducer, topic);
    }

    @Override
    public SensorEventType getMessageType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }

    @Override
    protected SwitchSensorAvro mapToAvro(SensorEvent sensorEvent) {
        SwitchSensorEvent event = (SwitchSensorEvent) sensorEvent;
        return SwitchSensorAvro.newBuilder()
                .setState(event.getState())
                .build();
    }
}