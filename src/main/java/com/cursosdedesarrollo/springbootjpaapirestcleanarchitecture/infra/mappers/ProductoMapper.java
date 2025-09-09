package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.mappers;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities.ProductoEntity;

public class ProductoMapper {

    public static ProductoEntity toEntity(Producto producto) {
        return new ProductoEntity(producto.getId(), producto.getNombre(), producto.getPrecio());
    }

    public static Producto toDomain(ProductoEntity entity) {
        return new Producto(entity.getId(), entity.getNombre(), entity.getPrecio());
    }
}
