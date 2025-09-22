package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos;

public class ProductoInsertOrUpdate {
    private String nombre;
    private double precio;

    public ProductoInsertOrUpdate() { }

    public ProductoInsertOrUpdate(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }


    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
}
