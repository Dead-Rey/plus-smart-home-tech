package ru.yandex.practicum.shoppingcart.exception;

public class ProductNotFoundInWarehouseException extends RuntimeException {
    public ProductNotFoundInWarehouseException(String message) {
        super(message);
    }
}