package ru.yandex.practicum.kafka.telemetry.collector.handler.sensor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SwitchSensorProto;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.telemetry.collector.service.handler.KafkaEventProducer;

@Service
public class SwitchSensorEventHandler extends BaseSensorEventHandler<SwitchSensorAvro> {

    public SwitchSensorEventHandler(KafkaEventProducer kafkaEventProducer,
                                    @Value("${kafka.topic.sensor}") String topic) {
        super(kafkaEventProducer, topic);
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.SWITCH_SENSOR_PROTO;
    }

    @Override
    protected SwitchSensorAvro mapToAvro(SensorEventProto sensorEvent) {
        SwitchSensorProto event = sensorEvent.getSwitchSensorProto();
        return SwitchSensorAvro.newBuilder()
                .setState(event.getState())
                .build();
    }
}