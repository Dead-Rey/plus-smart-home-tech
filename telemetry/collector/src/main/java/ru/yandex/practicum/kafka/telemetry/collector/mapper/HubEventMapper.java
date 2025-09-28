package ru.yandex.practicum.kafka.telemetry.collector.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.collector.model.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.stream.Collectors;

@Component
public class HubEventMapper {

    public HubEventAvro toAvro(BaseHubEvent event) {
        if (event == null) {
            return null;
        }

        HubEventAvro hubEvent = HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp().toEpochMilli())
                .setPayload(mapPayload(event))
                .build();

        return hubEvent;
    }

    private Object mapPayload(BaseHubEvent event) {
        if (event instanceof DeviceAddedEvent) {
            DeviceAddedEvent deviceAdded = (DeviceAddedEvent) event;
            return DeviceAddedEventAvro.newBuilder()
                    .setId(deviceAdded.getId())
                    .setType(mapDeviceType(deviceAdded.getDeviceType()))
                    .build();
        } else if (event instanceof DeviceRemovedEvent) {
            DeviceRemovedEvent deviceRemoved = (DeviceRemovedEvent) event;
            return DeviceRemovedEventAvro.newBuilder()
                    .setId(deviceRemoved.getId())
                    .build();
        } else if (event instanceof ScenarioAddedEvent) {
            ScenarioAddedEvent scenarioAdded = (ScenarioAddedEvent) event;
            return ScenarioAddedEventAvro.newBuilder()
                    .setName(scenarioAdded.getName())
                    .setConditions(scenarioAdded.getConditions().stream()
                            .map(this::mapScenarioCondition)
                            .collect(Collectors.toList()))
                    .setActions(scenarioAdded.getActions().stream()
                            .map(this::mapDeviceAction)
                            .collect(Collectors.toList()))
                    .build();
        } else if (event instanceof ScenarioRemovedEvent) {
            ScenarioRemovedEvent scenarioRemoved = (ScenarioRemovedEvent) event;
            return ScenarioRemovedEventAvro.newBuilder()
                    .setName(scenarioRemoved.getName())
                    .build();
        }

        throw new IllegalArgumentException("Unknown hub event type: " + event.getClass().getSimpleName());
    }

    private DeviceTypeAvro mapDeviceType(DeviceType deviceType) {
        return DeviceTypeAvro.valueOf(deviceType.name());
    }

    private ScenarioConditionAvro mapScenarioCondition(ScenarioCondition condition) {
        ScenarioConditionAvro.Builder builder = ScenarioConditionAvro.newBuilder()
                .setSensorId(condition.getSensorId())
                .setType(ConditionTypeAvro.valueOf(condition.getType().name()))
                .setOperation(ConditionOperationAvro.valueOf(condition.getOperation().name()));

        if (condition.getValue() != null) {
            builder.setValue(condition.getValue());
        }

        return builder.build();
    }

    private DeviceActionAvro mapDeviceAction(DeviceAction action) {
        DeviceActionAvro.Builder builder = DeviceActionAvro.newBuilder()
                .setSensorId(action.getSensorId())
                .setType(ActionTypeAvro.valueOf(action.getType().name()));

        if (action.getValue() != null) {
            builder.setValue(action.getValue());
        }

        return builder.build();
    }
}