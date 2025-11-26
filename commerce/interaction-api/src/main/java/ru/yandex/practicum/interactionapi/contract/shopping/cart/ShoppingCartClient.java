package ru.yandex.practicum.interactionapi.contract.shopping.cart;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "shopping-cart", fallback = ShoppingCartClientFallback.class,
        fallbackFactory = ShoppingCartClientFallbackFactory.class)
public interface ShoppingCartClient extends ShoppingCartOperations {
}