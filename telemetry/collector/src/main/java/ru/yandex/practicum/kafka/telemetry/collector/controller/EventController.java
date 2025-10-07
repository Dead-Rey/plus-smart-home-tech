package ru.yandex.practicum.kafka.telemetry.collector.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.kafka.telemetry.collector.handler.hub.HubEventHandler;
import ru.yandex.practicum.kafka.telemetry.collector.handler.sensor.SensorEventHandler;
import ru.yandex.practicum.kafka.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.kafka.telemetry.collector.model.hub.HubEventType;
import ru.yandex.practicum.kafka.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.kafka.telemetry.collector.model.sensor.SensorEventType;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/events")
public class EventController {

    private final Map<SensorEventType, SensorEventHandler> sensorEventHandlers;
    private final Map<HubEventType, HubEventHandler> hubEventHandlers;

    public EventController(Set<SensorEventHandler> sensorEventHandlers, Set<HubEventHandler> hubEventHandlers) {
        this.sensorEventHandlers = sensorEventHandlers.stream()
                .collect(Collectors.toMap(SensorEventHandler::getMessageType, Function.identity()));
        this.hubEventHandlers = hubEventHandlers.stream()
                .collect(Collectors.toMap(HubEventHandler::getMessageType, Function.identity()));
    }

    @PostMapping("/sensors")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void collectSensorEvent(@Valid @RequestBody SensorEvent event) {
        log.info("Sensor json: {}", event.toString());
        SensorEventHandler sensorEventHandler = sensorEventHandlers.get(event.getType());
        if (sensorEventHandler == null) {
            throw new IllegalArgumentException("Подходящий обработчик для события датчика " + event.getType() +
                    " не найден");
        }
        sensorEventHandler.handle(event);
    }

    @PostMapping("/hubs")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void collectHubEvent(@Valid @RequestBody HubEvent event) {
        log.info("Hub json: {}", event.toString());
        HubEventHandler hubEventHandler = hubEventHandlers.get(event.getType());
        if (hubEventHandler == null) {
            throw new IllegalArgumentException("Подходящий обработчик для события хаба " + event.getType() +
                    " не найден");
        }
        hubEventHandler.handle(event);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        String errorMessage = "Ошибка валидации: " + String.join("; ", errors);

        ErrorMessage errorResponse = ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .message(errorMessage)
                .details(Map.of("validationErrors", errors))
                .build();

        log.warn("Validation error: {}", errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException exception) {
        ErrorMessage errorResponse = ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        log.warn("Illegal argument error: {}", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGeneralException(Exception exception) {
        ErrorMessage errorResponse = ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("Произошла внутренняя ошибка сервера")
                .build();

        log.error("Internal server error: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}