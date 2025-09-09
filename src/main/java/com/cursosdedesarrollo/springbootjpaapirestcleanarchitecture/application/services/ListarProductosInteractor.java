package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.ListarProductosUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarProductosInteractor implements ListarProductosUseCase {

    private final ProductoRepository repository;

    public ListarProductosInteractor(ProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductoView> listar() {
        return repository.findAll().stream()
                .map(ListarProductosInteractor::toView)
                .toList();
    }

    private static ProductoView toView(Producto p) {
        return new ProductoView(p.getId(), p.getNombre(), p.getPrecio());
    }
}
