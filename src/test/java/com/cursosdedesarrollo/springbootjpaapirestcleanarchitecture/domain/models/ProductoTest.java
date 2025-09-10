package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de Producto")
class ProductoTest {

    @Nested
    @DisplayName("Constructor")
    class ConstructorTests {

        @Test
        @DisplayName("Crea producto válido")
        void creaProductoValido() {
            Producto p = new Producto(1L, "Teclado", 25.50);
            assertAll(
                    () -> assertEquals(1L, p.getId()),
                    () -> assertEquals("Teclado", p.getNombre()),
                    () -> assertEquals(25.50, p.getPrecio(), 1e-9)
            );
        }

        @Test
        @DisplayName("Falla si id es null")
        void fallaIdNull() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> new Producto(null, "A", 1.0));
            assertTrue(ex.getMessage().toLowerCase().contains("id"));
        }

        @ParameterizedTest(name = "Falla si id = {0} (<= 0)")
        @ValueSource(longs = {0L, -1L, -10L})
        void fallaIdNoPositivo(long id) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Producto(id, "A", 1.0));
        }

        @ParameterizedTest(name = "Falla si nombre = \"{0}\"")
        @ValueSource(strings = { "", " ", "   " })
        void fallaNombreBlanco(String nombre) {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> new Producto(1L, nombre, 1.0));
            assertTrue(ex.getMessage().toLowerCase().contains("nombre"));
        }

        @Test
        @DisplayName("Falla si nombre es null")
        void fallaNombreNull() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Producto(1L, null, 1.0));
        }

        @ParameterizedTest(name = "Falla si precio = {0} (<= 0)")
        @ValueSource(doubles = {0.0, -0.01, -10.0})
        void fallaPrecioNoPositivo(double precio) {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> new Producto(1L, "A", precio));
            assertTrue(ex.getMessage().toLowerCase().contains("precio"));
        }
    }

    @Nested
    @DisplayName("aplicarDescuento")
    class DescuentoTests {

        @ParameterizedTest(name = "Aplica {0}% sobre {1} => esperado {2}")
        @CsvSource({
                "10, 100.00, 90.00",
                "25, 200.00, 150.00",
                "50,  50.00, 25.00",
                "1,   99.99, 98.9901"
        })
        void aplicaDescuentoValido(double porcentaje, double precioInicial, double esperado) {
            Producto p = new Producto(1L, "Prod", precioInicial);
            p.aplicarDescuento(porcentaje);
            assertEquals(esperado, p.getPrecio(), 1e-6);
        }

        @ParameterizedTest(name = "Porcentaje inválido: {0}")
        @ValueSource(doubles = {0.0, -1.0, -50.0, 100.0, 150.0})
        void porcentajeInvalidoLanzaExcepcion(double porcentaje) {
            Producto p = new Producto(1L, "Prod", 100.0);
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> p.aplicarDescuento(porcentaje));
            assertTrue(ex.getMessage().toLowerCase().contains("inválido"));
        }

        @Test
        @DisplayName("Descuentos sucesivos son acumulativos (no aditivos)")
        void descuentosAcumulativos() {
            Producto p = new Producto(1L, "Prod", 100.0);
            p.aplicarDescuento(10); // 100 -> 90
            p.aplicarDescuento(10); // 90 -> 81
            assertEquals(81.0, p.getPrecio(), 1e-9);
        }
    }

    @Nested
    @DisplayName("Getters y Setters")
    class GettersSettersTests {

        @Test
        @DisplayName("Setters actualizan los campos")
        void settersActualizan() {
            Producto p = new Producto(1L, "A", 10.0);
            p.setId(2L);
            p.setNombre("B");
            p.setPrecio(20.0);

            assertAll(
                    () -> assertEquals(2L, p.getId()),
                    () -> assertEquals("B", p.getNombre()),
                    () -> assertEquals(20.0, p.getPrecio(), 1e-9)
            );
        }

        @Test
        @DisplayName("El constructor por defecto permite estado sin validar (documentado)")
        void constructorPorDefecto() {
            Producto p = new Producto(); // precio=0.0, id=null, nombre=null
            // Documentamos el estado inicial sin validación
            assertAll(
                    () -> assertNull(p.getId()),
                    () -> assertNull(p.getNombre()),
                    () -> assertEquals(0.0, p.getPrecio(), 1e-9)
            );
        }
    }
}
