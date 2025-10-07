package ru.yandex.practicum.kafka.telemetry.collector.controller;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ErrorMessage {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, Object> details;
}