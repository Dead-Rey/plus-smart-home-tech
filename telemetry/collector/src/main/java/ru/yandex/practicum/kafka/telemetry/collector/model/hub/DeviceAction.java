package ru.yandex.practicum.kafka.telemetry.collector.model.hub;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class DeviceAction {
    private String sensorId;
    private DeviceActionType type;
    private Integer value;


}