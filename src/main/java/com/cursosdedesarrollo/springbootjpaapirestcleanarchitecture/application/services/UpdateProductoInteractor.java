package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsert;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.UpdateProductoUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;

public class UpdateProductoInteractor implements UpdateProductoUseCase {

    private final ProductoRepository repository;

    public UpdateProductoInteractor(ProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductoView update(Long id, ProductoInsert update) {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        producto.setNombre(update.getNombre());
        producto.setPrecio(update.getPrecio());
        producto = repository.guardar(producto);
        return new ProductoView(producto.getId(), producto.getNombre(), producto.getPrecio());
    }
}
