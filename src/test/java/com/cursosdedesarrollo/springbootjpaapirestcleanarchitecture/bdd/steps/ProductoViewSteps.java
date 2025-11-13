package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.bdd.steps;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import io.cucumber.java.es.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoViewSteps {

    private ProductoView producto;

    // --- Given / When ---

    @Dado("un ProductoView creado sin parámetros")
    public void unProductoViewCreadoSinParametros() {
        producto = new ProductoView();
    }

    @Cuando(value = "creo un ProductoView con id {long}, nombre {string} y precio {double}")
    public void creoUnProductoViewConIdNombreYPrecio(long id,
                                                     String nombre,
                                                     double precio) {
        producto = new ProductoView(id, nombre, precio);
    }

    @Cuando("asigno id {long}, nombre {string} y precio {double}")
    public void asignoIdNombreYPrecio(long id, String nombre, double precio) {
        assertNotNull(producto, "Debe haberse creado el ProductoView previamente");
        producto.setId(id);
        producto.setNombre(nombre);
        producto.setPrecio(precio);
    }

    // --- Then ---

    @Entonces("el id debe ser null")
    public void elIdDebeSerNull() {
        assertNull(producto.getId(), "El ID debe ser null por defecto");
    }

    @Entonces("el nombre debe ser null")
    public void elNombreDebeSerNull() {
        assertNull(producto.getNombre(), "El nombre debe ser null por defecto");
    }

    @Entonces("el precio debe ser {double}")
    public void elPrecioDebeSer(double esperado) {
        assertEquals(esperado, producto.getPrecio(), 1e-9);
    }

    @Entonces("el id debe ser {long}")
    public void elIdDebeSer(long esperado) {
        assertEquals(esperado, producto.getId());
    }

    @Entonces("el nombre debe ser {string}")
    public void elNombreDebeSer(String esperado) {
        assertEquals(esperado, producto.getNombre());
    }
}

