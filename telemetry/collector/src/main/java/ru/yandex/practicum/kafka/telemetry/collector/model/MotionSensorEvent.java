package ru.yandex.practicum.kafka.telemetry.collector.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class MotionSensorEvent extends BaseSensorEvent {
    @NotNull
    private Integer linkQuality;

    @NotNull
    private Boolean motion;

    @NotNull
    private Integer voltage;

    public Integer getLinkQuality() {
        return linkQuality;
    }

    public Boolean getMotion() {
        return motion;
    }

    public Integer getVoltage() {
        return voltage;
    }

    @Override
    public SensorEventType getType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }
}