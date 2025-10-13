package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de Inventory")
class InventoryTest {

    @Nested
    @DisplayName("Constructor")
    class ConstructorTests {

        @Test
        @DisplayName("Crea producto válido")
        void creaProductoValido() {
            // Given Punto inicial
            // When accion a realizar
            Inventory i = new Inventory(1L, 100);
            // Then resultado esperado
            assertAll(
                    () -> assertEquals(1L, i.getId()),
                    () -> assertEquals(100, i.getQuantity())
            );
        }

        @Test
        @DisplayName("Falla si id es null")
        void fallaIdNull() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> new Inventory(null, 100));
            assertTrue(ex.getMessage().toLowerCase().contains("id"));
        }

        @ParameterizedTest(name = "Falla si id = {0} (<= 0)")
        @ValueSource(longs = {0L, -1L, -10L})
        void fallaIdNoPositivo(long id) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Producto(id, "A", 1.0));
        }



        @Test
        @DisplayName("Falla si la cantidad es negativa")
        void fallaNombreNull() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> new Inventory(1L, -1));
            assertTrue(ex.getMessage().toLowerCase().contains("cantidad"));
        }
    }

    @Test
    void pruebaSettersYGetters() {
        Inventory i = new Inventory();
        i.setId(2L);
        i.setQuantity(50);
        assertAll(
                () -> assertEquals(2L, i.getId()),
                () -> assertEquals(50, i.getQuantity())
        );
    }

}
