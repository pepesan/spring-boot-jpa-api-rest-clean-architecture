package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.validator;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;

public class ProductoValidator {
    public static void validate(Producto producto) {
        if (producto.getId() == null || producto.getId() <= 0)
            throw new IllegalArgumentException("El id debe existir y ser mayor que 0");
        if (producto.getNombre() == null || producto.getNombre().isBlank()) throw new IllegalArgumentException("nombre requerido");
        if (producto.getPrecio() <= 0) throw new IllegalArgumentException("precio debe ser > 0");
    }
    public static void validateDiscount(double porcentaje) {
        if (porcentaje <= 0 || porcentaje >= 100) {
            throw new IllegalArgumentException("Porcentaje inválido");
        }
    }
}
