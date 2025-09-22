package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProductoByIdInteractorTest {

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private GetProductoByIdInteractor interactor;

    @Test
    @DisplayName("obtenerPorId(): devuelve ProductoView si existe el producto")
    void obtenerPorId_devuelveProductoView() {
        // given
        Producto producto = new Producto(1L, "Monitor", 150.0);
        when(repository.findById(1L)).thenReturn(Optional.of(producto));

        // when
        ProductoView view = interactor.obtenerPorId(1L);

        // then
        assertNotNull(view);
        assertEquals(1L, view.getId());
        assertEquals("Monitor", view.getNombre());
        assertEquals(150.0, view.getPrecio(), 1e-9);
        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("obtenerPorId(): lanza excepción si no existe el producto")
    void obtenerPorId_productoNoExiste_lanzaExcepcion() {
        // given
        when(repository.findById(2L)).thenReturn(Optional.empty());

        // when
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> interactor.obtenerPorId(2L));
        // then
        assertEquals("Producto no encontrado", ex.getMessage());
        verify(repository).findById(2L);
        verifyNoMoreInteractions(repository);
    }
}
