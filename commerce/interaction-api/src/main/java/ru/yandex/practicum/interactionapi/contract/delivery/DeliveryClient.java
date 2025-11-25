package ru.yandex.practicum.interactionapi.contract.delivery;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.interactionapi.dto.delivery.DeliveryDto;
import ru.yandex.practicum.interactionapi.dto.order.OrderDto;


import java.math.BigDecimal;
import java.util.UUID;

@FeignClient(name = "delivery", path = "/api/v1/delivery", fallback = DeliveryClientFallback.class)
public interface DeliveryClient extends DeliveryOperations {

    @PutMapping
    DeliveryDto delivery(@RequestBody DeliveryDto deliveryDto);

    @PostMapping("/cost")
    BigDecimal deliveryCost(@RequestBody OrderDto orderDto);

    @PostMapping("/picked")
    void deliveryPicked(@RequestBody UUID orderId);

    @PostMapping("/successful")
    void deliverySuccessful(@RequestBody UUID orderId);

    @PostMapping("/failed")
    void deliveryFailed(@RequestBody UUID orderId);
}