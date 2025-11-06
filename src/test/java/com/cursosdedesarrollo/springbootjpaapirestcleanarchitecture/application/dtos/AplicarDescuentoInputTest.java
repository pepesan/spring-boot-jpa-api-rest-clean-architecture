package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AplicarDescuentoInputTest {

    @Test
    @DisplayName("Debe permitir asignar y leer valores correctamente")
    void asignarYLeerValores() {
        AplicarDescuentoInput input = new AplicarDescuentoInput();

        input.idProducto = 10L;
        input.porcentaje = 15.5;

        assertEquals(10L, input.idProducto.longValue());
        assertEquals(15.5, input.porcentaje, 1e-9);
    }

    @Test
    @DisplayName("Por defecto los valores deben ser 0L y 0.0")
    void valoresPorDefecto() {
        AplicarDescuentoInput input = new AplicarDescuentoInput();

        assertNotNull(input.idProducto);
        assertEquals(0L, input.idProducto.longValue());
        assertEquals(0.0, input.porcentaje, 1e-9);
    }

    @Test
    @DisplayName("Constructor con parámetros asigna valores")
    void constructorParametrizado() {
        AplicarDescuentoInput input = new AplicarDescuentoInput(5L, 2.5);

        assertEquals(5L, input.idProducto.longValue());
        assertEquals(2.5, input.porcentaje, 1e-9);
    }
}