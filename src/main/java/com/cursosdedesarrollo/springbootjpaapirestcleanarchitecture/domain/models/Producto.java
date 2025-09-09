package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models;

public class Producto {
    private Long id;
    private String nombre;
    private double precio;

    public Producto() {}

    public Producto(Long id, String nombre, double precio) {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("El id debe existir y ser mayor que 0");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("nombre requerido");
        if (precio <= 0) throw new IllegalArgumentException("precio debe ser > 0");
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public void aplicarDescuento(double porcentaje) {
        if (porcentaje <= 0 || porcentaje >= 100) {
            throw new IllegalArgumentException("Porcentaje inválido");
        }
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
