package ru.yandex.practicum.shoppingcart.dal;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.shoppingcart.model.ShoppingCartEntity;
import ru.yandex.practicum.shoppingcart.model.ShoppingCartState;

import java.util.Optional;
import java.util.UUID;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, UUID> {

    Optional<ShoppingCartEntity> findByUsernameAndCartState(String username, ShoppingCartState cartState);

    @Query("SELECT sc FROM ShoppingCartEntity sc WHERE sc.username = :username AND sc.cartState = 'ACTIVE'")
    Optional<ShoppingCartEntity> findActiveCartByUsername(@Param("username") String username);

    boolean existsByUsernameAndCartState(String username, ShoppingCartState cartState);
}