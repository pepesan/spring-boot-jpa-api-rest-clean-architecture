package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.GetProductoByIdUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;

public class GetProductoByIdInteractor implements GetProductoByIdUseCase {

    private final ProductoRepository repository;

    public GetProductoByIdInteractor(ProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductoView obtenerPorId(Long id) {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        return new ProductoView(producto.getId(), producto.getNombre(), producto.getPrecio());
    }
}
