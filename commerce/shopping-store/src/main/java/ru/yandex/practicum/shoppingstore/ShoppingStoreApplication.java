package ru.yandex.practicum.shoppingstore;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCaching
@ConfigurationPropertiesScan
@EnableFeignClients(basePackages = "ru.yandex.practicum.contract")
public class ShoppingStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingStoreApplication.class, args);
    }
}