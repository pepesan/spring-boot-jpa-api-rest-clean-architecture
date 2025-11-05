package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.InventarioInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.InventarioView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Inventory;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.InventoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddInventarioInteractorTest {

    @Mock
    private InventoryRepository repository;

    @InjectMocks
    private AddInventarioInteractor interactor;

    @Test
    @DisplayName("add(): guarda el inventario y devuelve InventarioView correctamente")
    void add_inventarioValido_devuelveView() {
        // given
        InventarioInsertOrUpdate dto = new InventarioInsertOrUpdate(1L, 100);

        // simulamos que el repositorio devuelve la entidad guardada
        when(repository.guardar(any(Inventory.class))).thenAnswer(inv -> inv.getArgument(0));

        // when
        InventarioView view = interactor.add(dto);

        // then
        assertNotNull(view);
        assertEquals(1L, view.getId());
        assertEquals(100, view.getQuantity());

        // verificamos que se guardó un inventario con los valores correctos
        ArgumentCaptor<Inventory> captor = ArgumentCaptor.forClass(Inventory.class);
        verify(repository).guardar(captor.capture());
        Inventory enviado = captor.getValue();
        assertEquals(1L, enviado.getId());
        assertEquals(100, enviado.getQuantity());

        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("add(): permite crear inventarios con valores mínimos válidos")
    void add_inventarioConValoresMinimos_validos() {
        // given
        InventarioInsertOrUpdate dto = new InventarioInsertOrUpdate(1L, 1);
        when(repository.guardar(any(Inventory.class))).thenAnswer(inv -> inv.getArgument(0));

        // when
        InventarioView view = interactor.add(dto);

        // then
        assertEquals(1L, view.getId());
        assertEquals(1, view.getQuantity());
    }

    @Test
    @DisplayName("add(): lanza excepción si repository.guardar() lanza excepción (simulación de error de persistencia)")
    void add_errorDeRepositorio_lanzaExcepcion() {
        // given
        InventarioInsertOrUpdate dto = new InventarioInsertOrUpdate(5L, 50);
        when(repository.guardar(any(Inventory.class)))
                .thenThrow(new RuntimeException("Error de base de datos"));

        // when
        RuntimeException ex = assertThrows(RuntimeException.class, () -> interactor.add(dto));

        // then
        assertEquals("Error de base de datos", ex.getMessage());
        verify(repository).guardar(any(Inventory.class));
        verifyNoMoreInteractions(repository);
    }
}
