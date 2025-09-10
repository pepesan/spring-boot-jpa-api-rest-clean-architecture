package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos;

public class AplicarDescuentoOutput {
    public Long idProducto;
    public double precioFinal;

    public AplicarDescuentoOutput() {
        this.idProducto = 0L;
        this.precioFinal = 0.0;
    }

    public AplicarDescuentoOutput(Long idProducto, double precioFinal) {
        this.idProducto = idProducto;
        this.precioFinal = precioFinal;
    }
}
