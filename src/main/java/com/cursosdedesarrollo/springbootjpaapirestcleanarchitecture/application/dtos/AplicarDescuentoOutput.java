package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos;

public class AplicarDescuentoOutput {
    public Long idProducto;
    public double precioFinal;

    public AplicarDescuentoOutput(Long idProducto, double precioFinal) {
        this.idProducto = idProducto;
        this.precioFinal = precioFinal;
    }
}
