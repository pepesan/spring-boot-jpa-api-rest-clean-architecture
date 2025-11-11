package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.jparepository.SpringDataProductoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test de integración para ProductoEntity usando Spring Data JPA + H2.
 * Valida persistencia, lectura, actualización, borrado y generación de ID.
 */
@DataJpaTest
class ProductoEntityTest {

    @Autowired
    private SpringDataProductoRepository repository;

    @Test
    @DisplayName("Guardar un producto asigna un ID autogenerado y persiste correctamente")
    void save_persisteCorrectamente() {
        ProductoEntity producto = new ProductoEntity(null, "Teclado", 25.5);

        ProductoEntity guardado = repository.save(producto);

        assertNotNull(guardado.getId(), "El ID debe ser generado automáticamente");
        assertEquals("Teclado", guardado.getNombre());
        assertEquals(25.5, guardado.getPrecio(), 1e-9);

        // Verificamos que se guardó en BD
        Optional<ProductoEntity> enBD = repository.findById(guardado.getId());
        assertTrue(enBD.isPresent());
        assertEquals("Teclado", enBD.get().getNombre());
        assertEquals(25.5, enBD.get().getPrecio(), 1e-9);
    }

    @Test
    @DisplayName("findById devuelve Optional.empty() cuando no existe")
    void findById_inexistente() {
        Optional<ProductoEntity> resultado = repository.findById(999L);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findAll devuelve todos los registros guardados")
    void findAll_devuelveTodo() {
        repository.save(new ProductoEntity(null, "Monitor", 120.0));
        repository.save(new ProductoEntity(null, "Impresora", 90.0));

        List<ProductoEntity> productos = repository.findAll();

        assertEquals(2, productos.size());
        assertTrue(productos.stream().anyMatch(p -> "Monitor".equals(p.getNombre())));
        assertTrue(productos.stream().anyMatch(p -> "Impresora".equals(p.getNombre())));
    }

    @Test
    @DisplayName("Actualizar un producto modifica los datos en BD")
    void update_modificaDatos() {
        ProductoEntity producto = repository.save(new ProductoEntity(null, "Ratón", 15.0));

        producto.setNombre("Ratón gamer");
        producto.setPrecio(20.0);
        ProductoEntity actualizado = repository.save(producto);

        assertEquals(producto.getId(), actualizado.getId());
        assertEquals("Ratón gamer", actualizado.getNombre());
        assertEquals(20.0, actualizado.getPrecio(), 1e-9);

        ProductoEntity enBD = repository.findById(producto.getId()).orElseThrow();
        assertEquals("Ratón gamer", enBD.getNombre());
        assertEquals(20.0, enBD.getPrecio(), 1e-9);
    }

    @Test
    @DisplayName("deleteById elimina correctamente un producto")
    void deleteById_eliminaCorrectamente() {
        ProductoEntity producto = repository.save(new ProductoEntity(null, "Auriculares", 30.0));
        Long id = producto.getId();

        assertTrue(repository.existsById(id));

        repository.deleteById(id);

        assertFalse(repository.existsById(id));
        assertTrue(repository.findById(id).isEmpty());
    }

    @Test
    @DisplayName("saveAll persiste varios productos y genera IDs distintos")
    void saveAll_persisteLote() {
        ProductoEntity p1 = new ProductoEntity(null, "Producto A", 10.0);
        ProductoEntity p2 = new ProductoEntity(null, "Producto B", 20.0);

        List<ProductoEntity> guardados = repository.saveAll(List.of(p1, p2));

        assertEquals(2, guardados.size());
        assertNotNull(guardados.get(0).getId());
        assertNotNull(guardados.get(1).getId());
        assertNotEquals(guardados.get(0).getId(), guardados.get(1).getId());
    }
}

