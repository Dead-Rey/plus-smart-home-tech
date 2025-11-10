package ru.yandex.practicum.warehouse.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.interactionapi.dto.warehouse.DimensionDto;
import ru.yandex.practicum.interactionapi.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.model.WarehouseProductEntity;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WarehouseMapper {

    /**
     * Конвертирует NewProductInWarehouseRequest в WarehouseProductEntity
     */
    public static WarehouseProductEntity toEntity(NewProductInWarehouseRequest request) {
        if (request == null) {
            return null;
        }

        return WarehouseProductEntity.builder()
                .productId(request.getProductId())
                .fragile(request.getFragile())
                .width(request.getDimension() != null ? request.getDimension().getWidth() : null)
                .height(request.getDimension() != null ? request.getDimension().getHeight() : null)
                .depth(request.getDimension() != null ? request.getDimension().getDepth() : null)
                .weight(request.getWeight())
                .quantity(0L) // Начальное количество 0
                .build();
    }

    /**
     * Создает DimensionDto из Entity
     */
    public static DimensionDto toDimensionDto(WarehouseProductEntity entity) {
        if (entity == null) {
            return null;
        }

        return DimensionDto.builder()
                .width(entity.getWidth())
                .height(entity.getHeight())
                .depth(entity.getDepth())
                .build();
    }
}