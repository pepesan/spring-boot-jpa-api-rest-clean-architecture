package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoInsertTest {

    @Test
    @DisplayName("Constructor vacío inicializa con valores por defecto")
    void constructorVacio() {
        ProductoInsert producto = new ProductoInsert();

        assertNull(producto.getNombre(), "El nombre debe ser null por defecto");
        assertEquals(0.0, producto.getPrecio(), 1e-9, "El precio debe ser 0.0 por defecto");
    }

    @Test
    @DisplayName("Constructor con parámetros asigna valores correctamente")
    void constructorConParametros() {
        ProductoInsert producto = new ProductoInsert("Teclado", 25.5);

        assertEquals("Teclado", producto.getNombre());
        assertEquals(25.5, producto.getPrecio(), 1e-9);
    }

    @Test
    @DisplayName("Setters actualizan valores correctamente")
    void settersFuncionan() {
        ProductoInsert producto = new ProductoInsert();

        producto.setNombre("Ratón");
        producto.setPrecio(15.0);

        assertEquals("Ratón", producto.getNombre());
        assertEquals(15.0, producto.getPrecio(), 1e-9);
    }

    @Test
    @DisplayName("Getters devuelven los valores asignados")
    void gettersFuncionan() {
        ProductoInsert producto = new ProductoInsert();
        producto.setNombre("Monitor");
        producto.setPrecio(199.99);

        assertEquals("Monitor", producto.getNombre());
        assertEquals(199.99, producto.getPrecio(), 1e-9);
    }
}

