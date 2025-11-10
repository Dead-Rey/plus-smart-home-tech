package ru.yandex.practicum.shoppingstore.service;

import org.springframework.data.domain.Page;
import ru.yandex.practicum.interactionapi.dto.shopping.store.ProductCategory;
import ru.yandex.practicum.interactionapi.dto.shopping.store.ProductDto;
import ru.yandex.practicum.shoppingstore.model.SetProductQuantityStateRequest;


import java.util.UUID;

public interface ShoppingStoreService {

    Page<ProductDto> getProducts(ProductCategory category, int page, int size, String sort);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    boolean removeProductFromStore(UUID productId);

    boolean setProductQuantityState(SetProductQuantityStateRequest request);

    ProductDto getProduct(UUID productId);
}