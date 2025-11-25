package ru.yandex.practicum.payment.exception;

public class NotEnoughInfoInOrderToCalculateBusinessException extends RuntimeException {
    public NotEnoughInfoInOrderToCalculateBusinessException(String message) {
        super(message);
    }

    public NotEnoughInfoInOrderToCalculateBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}