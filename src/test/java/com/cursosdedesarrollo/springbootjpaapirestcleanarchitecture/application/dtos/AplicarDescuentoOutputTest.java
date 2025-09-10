package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AplicarDescuentoOutputTest {

    @Test
    @DisplayName("Constructor vacío asigna valores por defecto")
    void constructorVacio() {
        AplicarDescuentoOutput output = new AplicarDescuentoOutput();

        assertEquals(0L, output.idProducto);
        assertEquals(0.0, output.precioFinal, 1e-9);
    }

    @Test
    @DisplayName("Constructor con parámetros asigna valores correctamente")
    void constructorConParametros() {
        AplicarDescuentoOutput output = new AplicarDescuentoOutput(5L, 150.75);

        assertEquals(5L, output.idProducto);
        assertEquals(150.75, output.precioFinal, 1e-9);
    }

    @Test
    @DisplayName("Asignación manual de campos públicos funciona correctamente")
    void asignacionManual() {
        AplicarDescuentoOutput output = new AplicarDescuentoOutput();

        output.idProducto = 10L;
        output.precioFinal = 199.99;

        assertEquals(10L, output.idProducto);
        assertEquals(199.99, output.precioFinal, 1e-9);
    }
}

