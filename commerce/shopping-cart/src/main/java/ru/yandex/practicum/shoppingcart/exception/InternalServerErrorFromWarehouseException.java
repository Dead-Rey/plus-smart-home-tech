package ru.yandex.practicum.shoppingcart.exception;

public class InternalServerErrorFromWarehouseException extends RuntimeException {
    public InternalServerErrorFromWarehouseException(String message) {
        super(message);
    }
}