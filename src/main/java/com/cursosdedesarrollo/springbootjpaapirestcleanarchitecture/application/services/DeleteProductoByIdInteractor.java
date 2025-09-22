package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.DeleteProductoByIdUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.GetProductoByIdUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;

public class DeleteProductoByIdInteractor implements DeleteProductoByIdUseCase {

    private final ProductoRepository repository;

    public DeleteProductoByIdInteractor(ProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductoView borrarPorId(Long id) {
        Producto producto = repository.deleteById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        return new ProductoView(producto.getId(), producto.getNombre(), producto.getPrecio());
    }
}
