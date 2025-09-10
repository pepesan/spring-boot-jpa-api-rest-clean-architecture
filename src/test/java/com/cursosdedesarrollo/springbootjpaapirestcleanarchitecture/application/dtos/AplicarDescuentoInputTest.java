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

        assertEquals(10L, input.idProducto);
        assertEquals(15.5, input.porcentaje, 1e-9);
    }

    @Test
    @DisplayName("Por defecto los valores deben ser null y 0.0")
    void valoresPorDefecto() {
        AplicarDescuentoInput input = new AplicarDescuentoInput();

        assertNull(input.idProducto);
        assertEquals(0.0, input.porcentaje, 1e-9);
    }
}

