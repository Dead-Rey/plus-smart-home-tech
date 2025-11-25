package ru.yandex.practicum.shoppingcart.exception;

public class NotAuthorizedBusinessException extends RuntimeException {

    public NotAuthorizedBusinessException(String message) {
        super(message);
    }

    public NotAuthorizedBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}