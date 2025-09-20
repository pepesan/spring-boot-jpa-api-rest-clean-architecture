package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoInput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoOutput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsert;
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
import org.springframework.context.annotation.Bean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddProductoInteractorTest {


    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private AddProductoInteractor interactor;

    @Test
    @DisplayName("add(): mapea desde ProductoInsert, guarda y devuelve ProductoView con ID asignado")
    void add_guardaYDevuelveView() {
        // arrange: input DTO
        ProductoInsert input = new ProductoInsert("Teclado", 25.5);

        // simulamos que el repo asigna ID al guardar (como haría JPA)
        when(repository.guardar(any(Producto.class))).thenAnswer(invocation -> {
            Producto enviado = invocation.getArgument(0);
            // No modificar 'enviado'. Devolvemos una NUEVA instancia con ID.
            return new Producto(10L, enviado.getNombre(), enviado.getPrecio());
        });

        // act
        ProductoView view = interactor.add(input);

        // assert
        assertNotNull(view);
        assertEquals(10L, view.getId());
        assertEquals("Teclado", view.getNombre());
        assertEquals(25.5, view.getPrecio(), 1e-9);

        // capturamos lo enviado al repo para asegurarnos del mapeo de entrada
        ArgumentCaptor<Producto> captor = ArgumentCaptor.forClass(Producto.class);
        verify(repository).guardar(captor.capture());
        Producto enviado = captor.getValue();
        assertNull(enviado.getId()); // el interactor no asigna ID
        assertEquals("Teclado", enviado.getNombre());
        assertEquals(25.5, enviado.getPrecio(), 1e-9);

        verifyNoMoreInteractions(repository);
    }
}

