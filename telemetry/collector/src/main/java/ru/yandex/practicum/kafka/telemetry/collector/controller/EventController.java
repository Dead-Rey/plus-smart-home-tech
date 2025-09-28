package ru.yandex.practicum.kafka.telemetry.collector.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.kafka.telemetry.collector.model.BaseHubEvent;
import ru.yandex.practicum.kafka.telemetry.collector.model.BaseSensorEvent;
import ru.yandex.practicum.kafka.telemetry.collector.service.EventProcessingService;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventProcessingService eventProcessingService;

    @PostMapping("/sensors")
    public ResponseEntity<Void> collectSensorEvent(@Valid @RequestBody BaseSensorEvent event) {
        log.debug("Received sensor event: {}", event);
        eventProcessingService.processSensorEvent(event);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/hubs")
    public ResponseEntity<Void> collectHubEvent(@Valid @RequestBody BaseHubEvent event) {
        log.debug("Received hub event: {}", event);
        eventProcessingService.processHubEvent(event);
        return ResponseEntity.ok().build();
    }
}