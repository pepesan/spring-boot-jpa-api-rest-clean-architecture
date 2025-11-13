package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.unit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraTest {
    @Test
    void suma_DosNumerosPositivos_RetornaSumaCorrecta() {
        Calculadora calc = new Calculadora();
        int resultado = calc.suma(3, 5);
        assertEquals(8, resultado);
    }

    @Test
    void resta_DosNumerosPositivos_RetornaRestaCorrecta() {
        Calculadora calc = new Calculadora();
        int resultado = calc.resta(5,3);
        assertEquals(2, resultado);
    }
    @Test
    void division_DosNumerosPositivos_RetornaDivisionCorrecta() {
        Calculadora calc = new Calculadora();
        Float resultado = calc.division(6.0F,3.0F);
        assertEquals(2.0, resultado, 0.001);
    }

    @Test
    void division_DivisionPorCero_LanzaArithmeticException() {
        Calculadora calc = new Calculadora();
        assertThrows(ArithmeticException.class, () -> {
            calc.division(6.0F, 0.0F);
        });
    }
}
