package ru.yandex.practicum.shoppingcart.config;


import feign.Response;
import feign.codec.ErrorDecoder;
import ru.yandex.practicum.shoppingcart.exception.InternalServerErrorFromWarehouseException;
import ru.yandex.practicum.shoppingcart.exception.ProductNotFoundInWarehouseException;


public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 404) {
            return new ProductNotFoundInWarehouseException("Resource not found for method: " + methodKey);
        }

        if (response.status() == 500) {
            return new InternalServerErrorFromWarehouseException("Server error occurred");
        }

        return defaultDecoder.decode(methodKey, response);
    }
}