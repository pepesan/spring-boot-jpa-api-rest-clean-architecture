package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.dtos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ProductoInsertOrUpdateDTOTest {
    @Test
    void compruebaConstructorYGetters() {
        ProductoInsertOrUpdateDTO productoDTO = new ProductoInsertOrUpdateDTO("Producto A", 99.99);
        assertEquals("Producto A", productoDTO.getNombre());
        assertEquals(99.99, productoDTO.getPrecio());
    }
    @Test
    void compruebaSetters() {
        ProductoInsertOrUpdateDTO productoDTO = new ProductoInsertOrUpdateDTO();
        productoDTO.setNombre("Producto B");
        productoDTO.setPrecio(49.99);
        assertEquals("Producto B", productoDTO.getNombre());
        assertEquals(49.99, productoDTO.getPrecio());
    }
}
