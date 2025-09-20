package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.validator.ProductoValidator;

public  class Producto {
    private Long id;
    private String nombre;
    private double precio;

    public Producto() {}

    public Producto(Long id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        ProductoValidator.validate(this);
    }

    public void aplicarDescuento(double porcentaje) {
        ProductoValidator.validateDiscount(porcentaje);
        this.precio -= this.precio * (porcentaje / 100);
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
