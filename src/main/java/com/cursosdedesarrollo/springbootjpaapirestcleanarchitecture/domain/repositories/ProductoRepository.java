package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository {
    Optional<Producto> findById(Long id);
    Producto guardar(Producto producto);
    List<Producto> findAll();
}
