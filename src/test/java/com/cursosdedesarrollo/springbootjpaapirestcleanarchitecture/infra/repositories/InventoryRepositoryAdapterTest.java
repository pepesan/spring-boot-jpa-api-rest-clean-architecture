package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.repositories;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Inventory;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities.InventoryEntity;
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
class InventoryRepositoryAdapterTest {

    @Mock
    private SpringDataInventoryRepository jpaRepository;

    @InjectMocks
    private InventoryRepositoryAdapter adapter;

    @Test
    @DisplayName("findById: devuelve el dominio mapeado cuando existe")
    void findById_hit() {
        InventoryEntity entity = new InventoryEntity();
        entity.setId(1L);
        entity.setQuantity(5);

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Inventory> res = adapter.findById(1L);

        assertTrue(res.isPresent());
        Inventory inv = res.get();
        assertEquals(1L, inv.getId());
        assertEquals(5, inv.getQuantity());

        verify(jpaRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    @DisplayName("findById: Optional.empty() cuando no existe")
    void findById_miss() {
        when(jpaRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Inventory> res = adapter.findById(999L);

        assertTrue(res.isEmpty());
        verify(jpaRepository).findById(999L);
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    @DisplayName("guardar: persiste el entity mapeado y devuelve el dominio mapeado del guardado")
    void guardar_ok() {
        Inventory entrada = new Inventory(1L, 3);

        InventoryEntity saved = new InventoryEntity();
        saved.setId(1L);
        saved.setQuantity(3);

        when(jpaRepository.save(any(InventoryEntity.class))).thenReturn(saved);

        Inventory resultado = adapter.guardar(entrada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(3, resultado.getQuantity());

        ArgumentCaptor<InventoryEntity> captor = ArgumentCaptor.forClass(InventoryEntity.class);
        verify(jpaRepository).save(captor.capture());
        InventoryEntity enviado = captor.getValue();
        assertEquals(1L, enviado.getId());
        assertEquals(3, enviado.getQuantity());

        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    @DisplayName("findAll: mapea la lista de entities a dominio")
    void findAll_ok() {
        InventoryEntity e1 = new InventoryEntity();
        e1.setId(1L); e1.setQuantity(10);

        InventoryEntity e2 = new InventoryEntity();
        e2.setId(2L); e2.setQuantity(20);

        when(jpaRepository.findAll()).thenReturn(List.of(e1, e2));

        List<Inventory> lista = adapter.findAll();

        assertNotNull(lista);
        assertEquals(2, lista.size());

        Inventory i1 = lista.stream().filter(i -> i.getId().equals(1L)).findFirst().orElseThrow();
        Inventory i2 = lista.stream().filter(i -> i.getId().equals(2L)).findFirst().orElseThrow();

        assertEquals(10, i1.getQuantity());
        assertEquals(20, i2.getQuantity());

        verify(jpaRepository).findAll();
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    @DisplayName("deleteById: elimina cuando existe y devuelve el mapeo")
    void deleteById_hit() {
        InventoryEntity entity = new InventoryEntity();
        entity.setId(1L);
        entity.setQuantity(7);

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Inventory> res = adapter.deleteById(1L);

        assertTrue(res.isPresent());
        Inventory inv = res.get();
        assertEquals(1L, inv.getId());
        assertEquals(7, inv.getQuantity());

        verify(jpaRepository).findById(1L);
        verify(jpaRepository).delete(entity);
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    @DisplayName("deleteById: devuelve empty cuando no existe")
    void deleteById_miss() {
        when(jpaRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Inventory> res = adapter.deleteById(999L);

        assertTrue(res.isEmpty());
        verify(jpaRepository).findById(999L);
        verifyNoMoreInteractions(jpaRepository);
    }
}