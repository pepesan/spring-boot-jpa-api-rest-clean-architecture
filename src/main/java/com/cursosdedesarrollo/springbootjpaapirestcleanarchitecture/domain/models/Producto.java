package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models;

public class Producto {
    private final String id;
    private String nombre;
    private double precio;

    public Producto(String id, String nombre, double precio) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id requerido");
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

    public String getId() {
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
