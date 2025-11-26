package ru.yandex.practicum.order.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.yandex.practicum.interactionapi.dto.order.OrderState;
import ru.yandex.practicum.order.model.OrderEntity;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByUsernameOrderByCreatedAtDesc(String username);

    Optional<OrderEntity> findByOrderIdAndUsername(UUID orderId, String username);

    List<OrderEntity> findByOrderState(OrderState state);

    @Query("SELECT o FROM OrderEntity o WHERE o.shoppingCartId = :shoppingCartId")
    Optional<OrderEntity> findByShoppingCartId(@Param("shoppingCartId") UUID shoppingCartId);

    boolean existsByOrderIdAndUsername(UUID orderId, String username);
}