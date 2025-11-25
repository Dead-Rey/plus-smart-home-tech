package ru.yandex.practicum.interactionapi.contract.delivery;



import org.springframework.data.domain.jaxb.SpringDataJaxb;
import ru.yandex.practicum.interactionapi.dto.delivery.DeliveryDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface DeliveryOperations {

    // Создать новую доставку в БД
    DeliveryDto delivery(DeliveryDto deliveryDto);

    // Расчёт полной стоимости доставки заказа
    BigDecimal deliveryCost(SpringDataJaxb.OrderDto orderDto);

    // Эмуляция получения товара в доставку
    void deliveryPicked(UUID orderId);

    // Эмуляция успешной доставки товара
    void deliverySuccessful(UUID orderId);

    // Эмуляция неудачного вручения товара
    void deliveryFailed(UUID orderId);
}