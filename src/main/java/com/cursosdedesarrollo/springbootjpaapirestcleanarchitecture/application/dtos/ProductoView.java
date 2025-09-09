package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos;

public class ProductoView {
    private String id;
    private String nombre;
    private double precio;

    public ProductoView() { }

    public ProductoView(String id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
}
