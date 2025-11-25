package ru.yandex.practicum.shoppingstore.dal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.interactionapi.dto.shopping.store.ProductCategory;
import ru.yandex.practicum.interactionapi.dto.shopping.store.ProductState;
import ru.yandex.practicum.shoppingstore.model.ProductEntity;


import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    Page<ProductEntity> findByProductCategoryAndProductState(
            ProductCategory productCategory,
            ProductState productState,
            Pageable pageable);

    Page<ProductEntity> findByProductCategory(
            ProductCategory productCategory,
            Pageable pageable);

    boolean existsByProductIdAndProductState(UUID productId, ProductState productState);
}