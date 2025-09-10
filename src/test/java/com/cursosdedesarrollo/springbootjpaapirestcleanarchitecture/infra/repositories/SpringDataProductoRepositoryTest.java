package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.repositories;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities.ProductoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.context.TestPropertySource; // opcional

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integración del repositorio JPA puro (sin adaptador).
 * Verifica persistencia, lectura, actualización, borrado y generación de ID.
 */
@DataJpaTest
// @TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create-drop") // opcional
class SpringDataProductoRepositoryTest {

    @Autowired
    private SpringDataProductoRepository repository;

    @Test
    @DisplayName("save: asigna ID (IDENTITY) y persiste campos")
    void save_asignaId_y_persiste() {
        ProductoEntity nuevo = new ProductoEntity(null, "Teclado", 25.5);
        ProductoEntity guardado = repository.save(nuevo);

        assertNotNull(guardado.getId(), "El ID debe generarse al guardar");
        assertEquals("Teclado", guardado.getNombre());
        assertEquals(25.5, guardado.getPrecio(), 1e-9);

        Optional<ProductoEntity> enBd = repository.findById(guardado.getId());
        assertTrue(enBd.isPresent());
        assertEquals("Teclado", enBd.get().getNombre());
        assertEquals(25.5, enBd.get().getPrecio(), 1e-9);
    }

    @Test
    @DisplayName("findById: Optional.empty() cuando no existe")
    void findById_inexistente() {
        Optional<ProductoEntity> res = repository.findById(999L);
        assertTrue(res.isEmpty());
    }

    @Test
    @DisplayName("findAll: devuelve todos los registros")
    void findAll_devuelveTodo() {
        repository.save(new ProductoEntity(null, "Monitor", 120.0));
        repository.save(new ProductoEntity(null, "Impresora", 90.0));

        List<ProductoEntity> lista = repository.findAll();
        assertEquals(2, lista.size());
        assertTrue(lista.stream().anyMatch(p -> "Monitor".equals(p.getNombre())));
        assertTrue(lista.stream().anyMatch(p -> "Impresora".equals(p.getNombre())));
    }

    @Test
    @DisplayName("update: modifica campos de un registro existente")
    void update_modificaCampos() {
        ProductoEntity e = repository.save(new ProductoEntity(null, "Ratón", 15.0));

        e.setNombre("Ratón gaming");
        e.setPrecio(19.99);
        ProductoEntity actualizado = repository.save(e);

        assertEquals(e.getId(), actualizado.getId());
        assertEquals("Ratón gaming", actualizado.getNombre());
        assertEquals(19.99, actualizado.getPrecio(), 1e-9);

        ProductoEntity enBd = repository.findById(e.getId()).orElseThrow();
        assertEquals("Ratón gaming", enBd.getNombre());
        assertEquals(19.99, enBd.getPrecio(), 1e-9);
    }

    @Test
    @DisplayName("deleteById: elimina un registro por su ID")
    void deleteById_elimina() {
        ProductoEntity e = repository.save(new ProductoEntity(null, "Auriculares", 30.0));
        Long id = e.getId();
        assertTrue(repository.existsById(id));

        repository.deleteById(id);

        assertFalse(repository.existsById(id));
        assertTrue(repository.findById(id).isEmpty());
    }

    @Test
    @DisplayName("saveAll: persiste en lote y genera IDs distintos")
    void saveAll_persisteLote() {
        ProductoEntity p1 = new ProductoEntity(null, "A", 10.0);
        ProductoEntity p2 = new ProductoEntity(null, "B", 20.0);

        List<ProductoEntity> guardados = repository.saveAll(List.of(p1, p2));

        assertEquals(2, guardados.size());
        Long id1 = guardados.get(0).getId();
        Long id2 = guardados.get(1).getId();

        assertNotNull(id1);
        assertNotNull(id2);
        assertNotEquals(id1, id2);

        assertEquals(2, repository.count());
    }
}
