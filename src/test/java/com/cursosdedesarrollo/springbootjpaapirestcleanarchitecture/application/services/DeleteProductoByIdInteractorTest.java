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
class DeleteProductoByIdInteractorTest {

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private DeleteProductoByIdInteractor interactor;

    @Test
    @DisplayName("borrarPorId(): devuelve ProductoView cuando el producto existía y se borró")
    void borrarPorId_productoExiste_devuelveProductoView() {
        // given
        Long id = 1L;
        Producto producto = new Producto(id, "Teclado", 25.0);
        when(repository.deleteById(id)).thenReturn(Optional.of(producto));

        // when
        ProductoView view = interactor.borrarPorId(id);

        // then
        assertNotNull(view);
        assertEquals(id, view.getId());
        assertEquals("Teclado", view.getNombre());
        assertEquals(25.0, view.getPrecio(), 1e-9);

        verify(repository).deleteById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("borrarPorId(): lanza excepción cuando el producto no existe")
    void borrarPorId_productoNoExiste_lanzaExcepcion() {
        // given
        Long id = 2L;
        when(repository.deleteById(id)).thenReturn(Optional.empty());

        // when
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> interactor.borrarPorId(id));

        // then
        assertEquals("Producto no encontrado", ex.getMessage());
        verify(repository).deleteById(id);
        verifyNoMoreInteractions(repository);
    }
}

