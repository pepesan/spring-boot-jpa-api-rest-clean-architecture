package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.adapters;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities.ProductoEntity;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.jparepository.SpringDataProductoRepository;
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

@ExtendWith(MockitoExtension.class)
class ProductoRepositoryAdapterTest {

    @Mock
    private SpringDataProductoRepository jpaRepository;

    @InjectMocks
    private ProductoRepositoryAdapter adapter;

    @Test
    @DisplayName("findById: devuelve el dominio mapeado cuando existe")
    void findById_hit() {
        ProductoEntity entity = new ProductoEntity();
        entity.setId(1L);
        entity.setNombre("Ratón");
        entity.setPrecio(25.0);

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Producto> res = adapter.findById(1L);

        assertTrue(res.isPresent());
        Producto p = res.get();
        assertEquals(1L, p.getId().longValue());
        assertEquals("Ratón", p.getNombre());
        assertEquals(25.0, p.getPrecio(), 1e-9);

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
        Producto entrada = new Producto(1L, "Teclado", 79.9);

        ProductoEntity saved = new ProductoEntity();
        saved.setId(1L);
        saved.setNombre("Teclado");
        saved.setPrecio(79.9);

        when(jpaRepository.save(any(ProductoEntity.class))).thenReturn(saved);

        Producto resultado = adapter.guardar(entrada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId().longValue());
        assertEquals("Teclado", resultado.getNombre());
        assertEquals(79.9, resultado.getPrecio(), 1e-9);

        ArgumentCaptor<ProductoEntity> captor = ArgumentCaptor.forClass(ProductoEntity.class);
        verify(jpaRepository).save(captor.capture());
        ProductoEntity enviado = captor.getValue();
        assertEquals(1L, enviado.getId().longValue());
        assertEquals("Teclado", enviado.getNombre());
        assertEquals(79.9, enviado.getPrecio(), 1e-9);

        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    @DisplayName("findAll: mapea la lista de entities a dominio")
    void findAll_ok() {
        ProductoEntity e1 = new ProductoEntity();
        e1.setId(1L); e1.setNombre("P1"); e1.setPrecio(10.0);

        ProductoEntity e2 = new ProductoEntity();
        e2.setId(2L); e2.setNombre("P2"); e2.setPrecio(20.0);

        when(jpaRepository.findAll()).thenReturn(List.of(e1, e2));

        List<Producto> lista = adapter.findAll();

        assertNotNull(lista);
        assertEquals(2, lista.size());

        Producto p1 = lista.stream().filter(i -> i.getId().equals(1L)).findFirst().orElseThrow();
        Producto p2 = lista.stream().filter(i -> i.getId().equals(2L)).findFirst().orElseThrow();

        assertEquals("P1", p1.getNombre());
        assertEquals(10.0, p1.getPrecio(), 1e-9);
        assertEquals("P2", p2.getNombre());
        assertEquals(20.0, p2.getPrecio(), 1e-9);

        verify(jpaRepository).findAll();
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    @DisplayName("deleteById: elimina cuando existe y devuelve el mapeo")
    void deleteById_hit() {
        ProductoEntity entity = new ProductoEntity();
        entity.setId(1L);
        entity.setNombre("Item");
        entity.setPrecio(15.5);

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Producto> res = adapter.deleteById(1L);

        assertTrue(res.isPresent());
        Producto p = res.get();
        assertEquals(1L, p.getId().longValue());
        assertEquals("Item", p.getNombre());
        assertEquals(15.5, p.getPrecio(), 1e-9);

        verify(jpaRepository).findById(1L);
        verify(jpaRepository).delete(entity);
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    @DisplayName("deleteById: devuelve empty cuando no existe")
    void deleteById_miss() {
        when(jpaRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Producto> res = adapter.deleteById(999L);

        assertTrue(res.isEmpty());
        verify(jpaRepository).findById(999L);
        verifyNoMoreInteractions(jpaRepository);
    }
}