package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventarioViewTest {

    @Test
    @DisplayName("Constructor vacío + setters funcionan correctamente")
    void constructorVacioYSetters() {
        InventarioView view = new InventarioView();
        view.setId(10L);
        view.setQuantity(200);

        assertEquals(10L, view.getId());
        assertEquals(200, view.getQuantity());
    }

    @Test
    @DisplayName("Constructor con argumentos asigna los campos correctamente")
    void constructorConArgumentos() {
        InventarioView view = new InventarioView(5L, 150);

        assertEquals(5L, view.getId());
        assertEquals(150, view.getQuantity());
    }

    @Test
    @DisplayName("Se pueden modificar los valores después de crear el objeto")
    void modificacionDeCampos() {
        InventarioView view = new InventarioView(1L, 50);
        view.setId(2L);
        view.setQuantity(99);

        assertEquals(2L, view.getId());
        assertEquals(99, view.getQuantity());
    }
}

