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

    @Test
    @DisplayName("ejecutar(): aplica descuento, guarda y devuelve output con precio final")
    void ejecutar_aplicaDescuentoYGuarda() {
        // arrange
        AplicarDescuentoInput in = new AplicarDescuentoInput();
        in.idProducto = 5L;
        in.porcentaje = 25.0;

        // el dominio exige constructor con validación
        Producto existente = new Producto(5L, "Monitor", 200.0);

        when(repository.findById(5L)).thenReturn(Optional.of(existente));
        // para guardar, devolvemos el mismo objeto (ya con precio modificado)
        when(repository.guardar(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        // act
        AplicarDescuentoOutput out = interactor.ejecutar(in);

        // assert: 25% de 200 => 150
        assertNotNull(out);
        assertEquals(5L, out.idProducto);
        assertEquals(150.0, out.precioFinal, 1e-9);

        // verificar que se llamó a guardar con el producto ya descontado
        ArgumentCaptor<Producto> captor = ArgumentCaptor.forClass(Producto.class);
        verify(repository).findById(5L);
        verify(repository).guardar(captor.capture());
        Producto guardado = captor.getValue();
        assertEquals(150.0, guardado.getPrecio(), 1e-9);

        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("ejecutar(): lanza IllegalArgumentException si el producto no existe")
    void ejecutar_productoNoEncontrado() {
        AplicarDescuentoInput in = new AplicarDescuentoInput();
        in.idProducto = 999L;
        in.porcentaje = 10.0;

        when(repository.findById(999L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> interactor.ejecutar(in));
        assertTrue(ex.getMessage().toLowerCase().contains("no encontrado"));

        verify(repository).findById(999L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("ejecutar(): porcentaje inválido se propaga desde el dominio y no guarda")
    void ejecutar_porcentajeInvalido_noGuarda() {
        AplicarDescuentoInput in = new AplicarDescuentoInput();
        in.idProducto = 7L;
        in.porcentaje = 0.0; // inválido: <= 0 o >= 100 lanza IllegalArgumentException

        Producto existente = new Producto(7L, "Ratón", 50.0);
        when(repository.findById(7L)).thenReturn(Optional.of(existente));

        assertThrows(IllegalArgumentException.class, () -> interactor.ejecutar(in));

        verify(repository).findById(7L);
        verify(repository, never()).guardar(any());
        verifyNoMoreInteractions(repository);
    }
}

