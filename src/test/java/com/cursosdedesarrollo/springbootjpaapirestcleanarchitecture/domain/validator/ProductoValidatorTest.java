package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.validator;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ProductoValidatorTest {

    // --------- validate(Producto) ---------

    @Test
    void productoValido_noLanzaExcepcion() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Camiseta");
        producto.setPrecio(10.0);

        assertDoesNotThrow(() -> ProductoValidator.validate(producto));
    }

    @Test
    void idNulo_lanzaExcepcion() {
        Producto producto = new Producto();
        producto.setId(null);
        producto.setNombre("Camiseta");
        producto.setPrecio(10.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> ProductoValidator.validate(producto));
        assertEquals("El id debe existir y ser mayor que 0", ex.getMessage());
    }

    @Test
    void idMenorIgualCero_lanzaExcepcion() {
        Producto producto = new Producto();
        producto.setId(0L);
        producto.setNombre("Camiseta");
        producto.setPrecio(10.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> ProductoValidator.validate(producto));
        assertEquals("El id debe existir y ser mayor que 0", ex.getMessage());
    }

    @Test
    void nombreNulo_lanzaExcepcion() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre(null);
        producto.setPrecio(10.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> ProductoValidator.validate(producto));
        assertEquals("nombre requerido", ex.getMessage());
    }

    @Test
    void nombreVacio_lanzaExcepcion() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("   ");
        producto.setPrecio(10.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> ProductoValidator.validate(producto));
        assertEquals("nombre requerido", ex.getMessage());
    }

    @Test
    void precioMenorIgualCero_lanzaExcepcion() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Camiseta");
        producto.setPrecio(0.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> ProductoValidator.validate(producto));
        assertEquals("precio debe ser > 0", ex.getMessage());
    }

    // --------- validateDiscount(double) ---------

    @Test
    void descuentoValido_noLanzaExcepcion() {
        assertDoesNotThrow(() -> ProductoValidator.validateDiscount(10.0));
    }

    @ParameterizedTest(name = "descuento inválido {0} lanza IllegalArgumentException")
    @ValueSource(doubles = {0.0, -5.0, 100.0, 150.0})
    void descuentoInvalido_lanzaExcepcion(double porcentaje) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> ProductoValidator.validateDiscount(porcentaje)
        );
        assertEquals("Porcentaje inválido", ex.getMessage());
    }
}
