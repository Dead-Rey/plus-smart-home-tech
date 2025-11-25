package ru.yandex.practicum.interactionapi.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.interactionapi.dto.shopping.cart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.AddressDto;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewOrderRequest {
    private ShoppingCartDto shoppingCart;
    private AddressDto deliveryAddress;
}