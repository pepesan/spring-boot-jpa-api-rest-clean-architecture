package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductoInsertOrUpdateDTO {
    @Size(min = 1, max = 100)
    private String nombre;

    @Positive
    @DecimalMin(value = "0.0", inclusive = false)
    private double precio;

    public ProductoInsertOrUpdateDTO() { }

    public ProductoInsertOrUpdateDTO(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }


    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
}
