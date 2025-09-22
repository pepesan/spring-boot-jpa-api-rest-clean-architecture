package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddProductoUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;


public class AddProductoInteractor implements AddProductoUseCase {

    private final ProductoRepository repository;


    public AddProductoInteractor(ProductoRepository repository) {
        this.repository = repository;
    }


    @Override
    public ProductoView add(ProductoInsertOrUpdate producto) {
        Producto p = new Producto();
        p.setNombre(producto.getNombre());
        p.setPrecio(producto.getPrecio());
        p = repository.guardar(p);

        return new ProductoView(p.getId(), p.getNombre(), p.getPrecio());
    }
}