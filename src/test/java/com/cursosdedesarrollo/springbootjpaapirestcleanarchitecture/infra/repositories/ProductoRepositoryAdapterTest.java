package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.repositories;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities.ProductoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios del adaptador. Mockeamos el repositorio JPA y
 * dejamos que el mapper estático haga su trabajo real.
 */
@ExtendWith(MockitoExtension.class)
class ProductoRepositoryAdapterTest {

    @Mock
    private SpringDataProductoRepository jpaRepository;

    @InjectMocks
    private ProductoRepositoryAdapter adapter;

    @Test
    @DisplayName("findById: devuelve el dominio mapeado cuando existe")
    void findById_hit() {
        // arrange
        ProductoEntity entity = new ProductoEntity();
        entity.setId(1L);
        entity.setNombre("Teclado");
        entity.setPrecio(25.5);

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));

        // act
        Optional<Producto> res = adapter.findById(1L);

        // assert
        assertTrue(res.isPresent());
        Producto p = res.get();
        assertEquals(1L, p.getId());
        assertEquals("Teclado", p.getNombre());
        assertEquals(25.5, p.getPrecio(), 1e-9);
        verify(jpaRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    @DisplayName("findById: Optional.empty() cuando no existe")
    void findById_miss() {
        when(jpaRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Producto> res = adapter.findById(999L);

        assertTrue(res.isEmpty());
        verify(jpaRepository).findById(999L);
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    @DisplayName("guardar: persiste el entity mapeado y devuelve el dominio mapeado del guardado")
    void guardar_ok() {
        // Given
        // arrange (dominio de entrada)
        Producto entrada = new Producto(1L, "Ratón", 15.0);

        // el repositorio devuelve el entity “persistido” (p.ej. igual, pero podría tener cambios)
        ProductoEntity saved = new ProductoEntity();
        saved.setId(1L);
        saved.setNombre("Ratón");
        saved.setPrecio(15.0);

        when(jpaRepository.save(any(ProductoEntity.class))).thenReturn(saved);

        // act
        // When
        Producto resultado = adapter.guardar(entrada);

        // Then
        // assert: devuelve lo mapeado desde el guardado
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Ratón", resultado.getNombre());
        assertEquals(15.0, resultado.getPrecio(), 1e-9);

        // capturamos el entity pasado a save para verificar el mapeo de ida
        ArgumentCaptor<ProductoEntity> captor = ArgumentCaptor.forClass(ProductoEntity.class);
        verify(jpaRepository).save(captor.capture());
        ProductoEntity enviado = captor.getValue();
        assertEquals(1L, enviado.getId());
        assertEquals("Ratón", enviado.getNombre());
        assertEquals(15.0, enviado.getPrecio(), 1e-9);

        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    @DisplayName("findAll: mapea la lista de entities a dominio")
    void findAll_ok() {
        ProductoEntity e1 = new ProductoEntity();
        e1.setId(1L); e1.setNombre("A"); e1.setPrecio(10.0);

        ProductoEntity e2 = new ProductoEntity();
        e2.setId(2L); e2.setNombre("B"); e2.setPrecio(20.0);

        when(jpaRepository.findAll()).thenReturn(List.of(e1, e2));

        List<Producto> lista = adapter.findAll();

        assertNotNull(lista);
        assertEquals(2, lista.size());

        Producto p1 = lista.stream().filter(p -> p.getId().equals(1L)).findFirst().orElseThrow();
        Producto p2 = lista.stream().filter(p -> p.getId().equals(2L)).findFirst().orElseThrow();

        assertEquals("A", p1.getNombre());
        assertEquals(10.0, p1.getPrecio(), 1e-9);

        assertEquals("B", p2.getNombre());
        assertEquals(20.0, p2.getPrecio(), 1e-9);

        verify(jpaRepository).findAll();
        verifyNoMoreInteractions(jpaRepository);
    }
}

