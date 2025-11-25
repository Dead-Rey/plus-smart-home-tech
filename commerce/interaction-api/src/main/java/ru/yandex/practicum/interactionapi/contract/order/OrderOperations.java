package ru.yandex.practicum.interactionapi.contract.order;


import ru.yandex.practicum.interactionapi.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.interactionapi.dto.order.OrderDto;
import ru.yandex.practicum.interactionapi.dto.order.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

public interface OrderOperations {
    // Получить заказы пользователя
    List<OrderDto> getClientOrders(String username);

    // Создать новый заказ
    OrderDto createNewOrder(String username, CreateNewOrderRequest request);

    // Возврат заказа
    OrderDto productReturn(ProductReturnRequest request);

    // Оплата заказа
    OrderDto payment(UUID orderId);

    // Ошибка оплаты
    OrderDto paymentFailed(UUID orderId);

    // Доставка заказа
    OrderDto delivery(UUID orderId);

    // Ошибка доставки
    OrderDto deliveryFailed(UUID orderId);

    // Завершение заказа
    OrderDto complete(UUID orderId);

    // Расчёт стоимости заказа
    OrderDto calculateTotalCost(UUID orderId);

    // Расчёт стоимости доставки
    OrderDto calculateDeliveryCost(UUID orderId);

    // Сборка заказа
    OrderDto assembly(UUID orderId);

    // Сборка заказа произошла с ошибкой.
    OrderDto assemblyFailed(UUID orderId);
}