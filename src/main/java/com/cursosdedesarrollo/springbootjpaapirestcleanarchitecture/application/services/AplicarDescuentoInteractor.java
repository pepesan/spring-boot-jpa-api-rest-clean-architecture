package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoInput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoOutput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AplicarDescuentoUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;


public class AplicarDescuentoInteractor implements AplicarDescuentoUseCase {

    private final ProductoRepository repository;


    public AplicarDescuentoInteractor(ProductoRepository repository) {
        this.repository = repository;
    }

    public AplicarDescuentoOutput ejecutar(AplicarDescuentoInput input) {
        Producto producto = repository.findById(input.idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        producto.aplicarDescuento(input.porcentaje);

        repository.guardar(producto);

        return new AplicarDescuentoOutput(producto.getId(), producto.getPrecio());
    }
}