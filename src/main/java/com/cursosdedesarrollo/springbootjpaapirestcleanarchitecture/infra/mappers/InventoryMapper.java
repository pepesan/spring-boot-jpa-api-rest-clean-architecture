package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.mappers;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Inventory;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities.InventoryEntity;

public class InventoryMapper {

    public static InventoryEntity toEntity(Inventory inventory) {
        return new InventoryEntity(inventory.getId(), inventory.getQuantity());
    }

    public static Inventory toDomain(InventoryEntity inventoryEntity) {
        return new Inventory(inventoryEntity.getId(), inventoryEntity.getQuantity());
    }
}
