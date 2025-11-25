package ru.yandex.practicum.shoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.yandex.practicum.interactionapi.contract.warehouse.WarehouseClient;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableFeignClients(basePackages = "ru.yandex.practicum.contract")
public class ShoppingCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingCartApplication.class, args);
    }
}