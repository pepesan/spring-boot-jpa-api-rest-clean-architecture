package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos;

public class AplicarDescuentoOutput {
    public String idProducto;
    public double precioFinal;

    public AplicarDescuentoOutput(String idProducto, double precioFinal) {
        this.idProducto = idProducto;
        this.precioFinal = precioFinal;
    }
}
