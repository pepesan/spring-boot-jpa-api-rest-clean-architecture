package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoViewTest {

    @Test
    @DisplayName("Constructor vacío inicializa con valores por defecto")
    void constructorVacio() {
        ProductoView producto = new ProductoView();

        assertNull(producto.getId(), "El ID debe ser null por defecto");
        assertNull(producto.getNombre(), "El nombre debe ser null por defecto");
        assertEquals(0.0, producto.getPrecio(), 1e-9, "El precio debe ser 0.0 por defecto");
    }

    @Test
    @DisplayName("Constructor con parámetros asigna valores correctamente")
    void constructorConParametros() {
        ProductoView producto = new ProductoView(1L, "Teclado", 25.5);

        assertEquals(1L, producto.getId());
        assertEquals("Teclado", producto.getNombre());
        assertEquals(25.5, producto.getPrecio(), 1e-9);
    }

    @Test
    @DisplayName("Setters actualizan los valores correctamente")
    void settersFuncionan() {
        ProductoView producto = new ProductoView();

        producto.setId(2L);
        producto.setNombre("Ratón");
        producto.setPrecio(15.0);

        assertEquals(2L, producto.getId());
        assertEquals("Ratón", producto.getNombre());
        assertEquals(15.0, producto.getPrecio(), 1e-9);
    }

    @Test
    @DisplayName("Getters devuelven los valores asignados")
    void gettersFuncionan() {
        ProductoView producto = new ProductoView();
        producto.setId(3L);
        producto.setNombre("Monitor");
        producto.setPrecio(199.99);

        assertEquals(3L, producto.getId());
        assertEquals("Monitor", producto.getNombre());
        assertEquals(199.99, producto.getPrecio(), 1e-9);
    }
}

