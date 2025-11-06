package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos;

public class AplicarDescuentoInput {
    public Long idProducto;
    public double porcentaje;

    public AplicarDescuentoInput() {
        this.idProducto = 0L;
        this.porcentaje = 0.0;
    }

    public AplicarDescuentoInput(Long idProducto, double porcentaje) {
        this.idProducto = idProducto;
        this.porcentaje = porcentaje;
    }

}
