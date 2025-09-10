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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarProductosInteractorTest {

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private ListarProductosInteractor interactor;

    @Test
    @DisplayName("listar(): devuelve lista de ProductoView mapeada correctamente")
    void listarProductos_ok() {
        // Arrange: simulamos 2 productos en el repositorio
        Producto p1 = new Producto(1L, "Teclado", 25.5);
        Producto p2 = new Producto(2L, "Ratón", 15.0);
        when(repository.findAll()).thenReturn(List.of(p1, p2));

        // Act
        List<ProductoView> resultado = interactor.listar();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        ProductoView v1 = resultado.get(0);
        ProductoView v2 = resultado.get(1);

        assertEquals(1L, v1.getId());
        assertEquals("Teclado", v1.getNombre());
        assertEquals(25.5, v1.getPrecio(), 1e-9);

        assertEquals(2L, v2.getId());
        assertEquals("Ratón", v2.getNombre());
        assertEquals(15.0, v2.getPrecio(), 1e-9);

        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("listar(): devuelve lista vacía si no hay productos")
    void listarProductos_vacio() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());

        // Act
        List<ProductoView> resultado = interactor.listar();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }
}

