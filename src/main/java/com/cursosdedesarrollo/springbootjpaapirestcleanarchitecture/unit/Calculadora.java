package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.unit;

public class Calculadora {
    public Integer suma(Integer a, Integer b) {
        return a + b;
    }
    public Integer resta(Integer a, Integer b) {
        return a - b;
    }

    public Float division(Float a, Float b) {
        if (b == 0) {
            throw new ArithmeticException("Division por cero no permitida");
        }
        return (float) a / b;
    }
}
