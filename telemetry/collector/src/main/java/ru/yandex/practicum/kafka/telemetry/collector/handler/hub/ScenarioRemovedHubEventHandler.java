package ru.yandex.practicum.kafka.telemetry.collector.handler.hub;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.collector.handler.KafkaEventProducer;
import ru.yandex.practicum.kafka.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.kafka.telemetry.collector.model.hub.HubEventType;
import ru.yandex.practicum.kafka.telemetry.collector.model.hub.ScenarioRemovedEvent;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;


@Service
public class ScenarioRemovedHubEventHandler extends BaseHubEventHandler<ScenarioRemovedEventAvro> {

    public ScenarioRemovedHubEventHandler(KafkaEventProducer kafkaEventProducer,
                                          @Value("${kafka.topic.hub}") String topic) {
        super(kafkaEventProducer, topic);
    }

    @Override
    protected ScenarioRemovedEventAvro mapToAvro(HubEvent hubEvent) {
        ScenarioRemovedEvent event = (ScenarioRemovedEvent) hubEvent;
        return ScenarioRemovedEventAvro.newBuilder()
                .setName(event.getName())
                .build();
    }

    @Override
    public HubEventType getMessageType() {
        return HubEventType.SCENARIO_REMOVED;
    }
}