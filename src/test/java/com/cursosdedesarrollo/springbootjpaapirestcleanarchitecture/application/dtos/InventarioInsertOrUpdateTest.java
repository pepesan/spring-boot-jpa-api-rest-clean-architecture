package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventarioInsertOrUpdateTest {

    @Test
    @DisplayName("Constructor vacío + setters funcionan correctamente")
    void constructorVacioYSetters() {
        InventarioInsertOrUpdate dto = new InventarioInsertOrUpdate();
        dto.setId(10L);
        dto.setQuantity(50);

        assertEquals(10L, dto.getId());
        assertEquals(50, dto.getQuantity());
    }

    @Test
    @DisplayName("Constructor con argumentos asigna los campos")
    void constructorConArgumentos() {
        InventarioInsertOrUpdate dto = new InventarioInsertOrUpdate(5L, 25);

        assertEquals(5L, dto.getId());
        assertEquals(25, dto.getQuantity());
    }

    @Test
    @DisplayName("Se puede modificar el quantity después de crear el DTO")
    void modificarQuantity() {
        InventarioInsertOrUpdate dto = new InventarioInsertOrUpdate(1L, 10);
        dto.setQuantity(99);

        assertEquals(1L, dto.getId());
        assertEquals(99, dto.getQuantity());
    }
}
