package ru.yandex.practicum.payment.exception;

import java.util.UUID;

public class NoOrderFoundBusinessException extends RuntimeException {
    private final UUID orderId;

    public NoOrderFoundBusinessException(UUID orderId) {
        super("Order not found with id: " + orderId);
        this.orderId = orderId;
    }

    public NoOrderFoundBusinessException(UUID orderId, String message) {
        super(message);
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }
}