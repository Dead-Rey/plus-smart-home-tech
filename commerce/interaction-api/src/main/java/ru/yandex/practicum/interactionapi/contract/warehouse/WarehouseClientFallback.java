package ru.yandex.practicum.interactionapi.contract.warehouse;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.interactionapi.dto.shopping.cart.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.*;


import java.util.Map;
import java.util.UUID;

@Component
public class WarehouseClientFallback  implements WarehouseClient {
    @Override
    public void newProductInWarehouse(NewProductInWarehouseRequest request) {
        throw new RuntimeException("Warehouse service is unavailable");
    }

    @Override
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto shoppingCartDto) {
        throw new RuntimeException("Warehouse service is unavailable");
    }

    @Override
    public void addProductToWarehouse(AddProductToWarehouseRequest request) {
        throw new RuntimeException("Warehouse service is unavailable");
    }

    @Override
    public AddressDto getWarehouseAddress() {
        throw new RuntimeException("Warehouse service is unavailable");
    }

    @Override
    public BookedProductsDto assemblyProductsForOrder(AssemblyProductsForOrderRequest request) {
        throw new RuntimeException("Warehouse service is unavailable");
    }

    @Override
    public void shippedToDelivery(ShippedToDeliveryRequest request) {
        throw new RuntimeException("Warehouse service is unavailable");
    }

    @Override
    public void acceptReturn(Map<UUID, Integer> returnedProducts) {
        throw new RuntimeException("Warehouse service is unavailable");
    }
}