package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductoInteractorTest {

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private UpdateProductoInteractor interactor;

    @Test
    @DisplayName("update(): actualiza y devuelve ProductoView cuando el producto existe")
    void update_productoExiste_devuelveProductoViewActualizado() {
        // given
        Long id = 1L;
        Producto existente = new Producto(id, "Viejo nombre", 10.0);
        when(repository.findById(id)).thenReturn(Optional.of(existente));

        ProductoInsertOrUpdate dto = new ProductoInsertOrUpdate("Nuevo nombre", 20.0);

        // simulamos que el repo guarda y devuelve el producto actualizado
        when(repository.guardar(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        // when
        ProductoView resultado = interactor.update(id, dto);

        // then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Nuevo nombre", resultado.getNombre());
        assertEquals(20.0, resultado.getPrecio(), 1e-9);

        // verificamos que se buscó
        verify(repository).findById(id);

        // capturamos lo que se guardó para asegurarnos que se actualizaron los campos
        ArgumentCaptor<Producto> captor = ArgumentCaptor.forClass(Producto.class);
        verify(repository).guardar(captor.capture());
        Producto guardado = captor.getValue();
        assertEquals("Nuevo nombre", guardado.getNombre());
        assertEquals(20.0, guardado.getPrecio(), 1e-9);

        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("update(): lanza excepción cuando el producto no existe")
    void update_productoNoExiste_lanzaExcepcion() {
        // given
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ProductoInsertOrUpdate dto = new ProductoInsertOrUpdate("Nombre", 30.0);

        // when
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> interactor.update(id, dto));

        // then
        assertEquals("Producto no encontrado", ex.getMessage());
        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }
}

