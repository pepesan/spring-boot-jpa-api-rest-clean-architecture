package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;

import java.util.Optional;

public interface ProductoRepository {
    Optional<Producto> buscarPorId(String id);
    Producto guardar(Producto producto);
}
