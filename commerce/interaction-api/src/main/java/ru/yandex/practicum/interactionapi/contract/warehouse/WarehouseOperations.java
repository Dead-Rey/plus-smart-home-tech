package ru.yandex.practicum.interactionapi.contract.warehouse;

import ru.yandex.practicum.interactionapi.dto.shopping.cart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.interactionapi.dto.warehouse.*;

import java.util.Map;
import java.util.UUID;

public interface WarehouseOperations {

    void newProductInWarehouse(NewProductInWarehouseRequest request);

    BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto shoppingCartDto);

    void addProductToWarehouse(AddProductToWarehouseRequest request);

    AddressDto getWarehouseAddress();

    BookedProductsDto assemblyProductsForOrder(AssemblyProductsForOrderRequest request);

    void shippedToDelivery(ShippedToDeliveryRequest request);

    void acceptReturn(Map<UUID, Integer> returnedProducts);
}