package ru.yandex.practicum.kafka.telemetry.collector.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.collector.model.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

@Component
public class SensorEventMapper {

    public SensorEventAvro toAvro(BaseSensorEvent event) {
        if (event == null) {
            return null;
        }

        SensorEventAvro sensorEvent = SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp().toEpochMilli())
                .setPayload(mapPayload(event))
                .build();

        return sensorEvent;
    }

    private Object mapPayload(BaseSensorEvent event) {
        if (event instanceof ClimateSensorEvent) {
            ClimateSensorEvent climateEvent = (ClimateSensorEvent) event;
            return ClimateSensorAvro.newBuilder()
                    .setTemperatureC(climateEvent.getTemperatureC())
                    .setHumidity(climateEvent.getHumidity())
                    .setCo2Level(climateEvent.getCo2Level())
                    .build();
        } else if (event instanceof LightSensorEvent) {
            LightSensorEvent lightEvent = (LightSensorEvent) event;
            LightSensorAvro.Builder builder = LightSensorAvro.newBuilder();
            if (lightEvent.getLinkQuality() != null) {
                builder.setLinkQuality(lightEvent.getLinkQuality());
            }
            if (lightEvent.getLuminosity() != null) {
                builder.setLuminosity(lightEvent.getLuminosity());
            }
            return builder.build();
        } else if (event instanceof MotionSensorEvent) {
            MotionSensorEvent motionEvent = (MotionSensorEvent) event;
            return MotionSensorAvro.newBuilder()
                    .setLinkQuality(motionEvent.getLinkQuality())
                    .setMotion(motionEvent.getMotion())
                    .setVoltage(motionEvent.getVoltage())
                    .build();
        } else if (event instanceof SwitchSensorEvent) {
            SwitchSensorEvent switchEvent = (SwitchSensorEvent) event;
            return SwitchSensorAvro.newBuilder()
                    .setState(switchEvent.getState())
                    .build();
        } else if (event instanceof TemperatureSensorEvent) {
            TemperatureSensorEvent tempEvent = (TemperatureSensorEvent) event;
            return TemperatureSensorAvro.newBuilder()
                    .setTemperatureC(tempEvent.getTemperatureC())
                    .setTemperatureF(tempEvent.getTemperatureF())
                    .build();
        }

        throw new IllegalArgumentException("Unknown sensor event type: " + event.getClass().getSimpleName());
    }
}