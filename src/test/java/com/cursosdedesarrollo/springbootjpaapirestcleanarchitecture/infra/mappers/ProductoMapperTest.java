package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.mappers;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities.ProductoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoMapperTest {

    @Test
    @DisplayName("toEntity: convierte correctamente un Producto a ProductoEntity")
    void toEntity_ok() {
        // given
        Producto producto = new Producto(1L, "Teclado", 25.5);

        // when
        ProductoEntity entity = ProductoMapper.toEntity(producto);

        // then
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Teclado", entity.getNombre());
        assertEquals(25.5, entity.getPrecio(), 1e-9);
    }

    @Test
    @DisplayName("toDomain: convierte correctamente un ProductoEntity a Producto")
    void toDomain_ok() {
        // given
        ProductoEntity entity = new ProductoEntity(2L, "Ratón", 15.0);

        // when
        Producto producto = ProductoMapper.toDomain(entity);

        // then
        assertNotNull(producto);
        assertEquals(2L, producto.getId());
        assertEquals("Ratón", producto.getNombre());
        assertEquals(15.0, producto.getPrecio(), 1e-9);
    }

    @Test
    @DisplayName("Conversión bidireccional: Producto -> Entity -> Producto mantiene los datos")
    void bidireccional_ok() {
        Producto original = new Producto(3L, "Monitor", 120.0);

        ProductoEntity entity = ProductoMapper.toEntity(original);
        Producto convertido = ProductoMapper.toDomain(entity);

        assertEquals(original.getId(), convertido.getId());
        assertEquals(original.getNombre(), convertido.getNombre());
        assertEquals(original.getPrecio(), convertido.getPrecio(), 1e-9);
    }

    @Test
    @DisplayName("toEntity: lanza NullPointerException si el Producto es null")
    void toEntity_null() {
        assertThrows(NullPointerException.class, () -> ProductoMapper.toEntity(null));
    }

    @Test
    @DisplayName("toDomain: lanza NullPointerException si el ProductoEntity es null")
    void toDomain_null() {
        assertThrows(NullPointerException.class, () -> ProductoMapper.toDomain(null));
    }
}

