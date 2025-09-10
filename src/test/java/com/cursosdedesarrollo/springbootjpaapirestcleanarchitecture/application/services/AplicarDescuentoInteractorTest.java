package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoInput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoOutput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AplicarDescuentoInteractorTest {

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private AplicarDescuentoInteractor interactor;

    @Test
    @DisplayName("ejecutar(): aplica descuento, guarda y retorna precio final correcto")
    void ejecutar_ok() {
        // arrange
        AplicarDescuentoInput in = new AplicarDescuentoInput();
        in.idProducto = 5L;
        in.porcentaje = 25.0; // 25% sobre 200 => 150

        Producto existente = new Producto(5L, "Monitor", 200.0);
        when(repository.findById(5L)).thenReturn(Optional.of(existente));
        when(repository.guardar(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        // act
        AplicarDescuentoOutput out = interactor.ejecutar(in);

        // assert
        assertNotNull(out);
        assertEquals(5L, out.idProducto);
        assertEquals(150.0, out.precioFinal, 1e-9);

        ArgumentCaptor<Producto> captor = ArgumentCaptor.forClass(Producto.class);
        verify(repository).findById(5L);
        verify(repository).guardar(captor.capture());
        Producto guardado = captor.getValue();
        assertEquals(150.0, guardado.getPrecio(), 1e-9);

        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("ejecutar(): lanza excepción si el producto no existe")
    void ejecutar_noExiste() {
        AplicarDescuentoInput in = new AplicarDescuentoInput();
        in.idProducto = 999L;
        in.porcentaje = 10.0;

        when(repository.findById(999L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> interactor.ejecutar(in));
        assertTrue(ex.getMessage().toLowerCase().contains("no encontrado"));

        verify(repository).findById(999L);
        verify(repository, never()).guardar(any());
        verifyNoMoreInteractions(repository);
    }

    @ParameterizedTest(name = "Porcentaje inválido: {0}")
    @ValueSource(doubles = {0.0, -1.0, -10.0, 100.0, 150.0})
    @DisplayName("ejecutar(): porcentaje inválido se propaga y no guarda")
    void ejecutar_porcentajeInvalido(double porcentaje) {
        AplicarDescuentoInput in = new AplicarDescuentoInput();
        in.idProducto = 7L;
        in.porcentaje = porcentaje;

        Producto existente = new Producto(7L, "Ratón", 50.0);
        when(repository.findById(7L)).thenReturn(Optional.of(existente));

        assertThrows(IllegalArgumentException.class, () -> interactor.ejecutar(in));

        verify(repository).findById(7L);
        verify(repository, never()).guardar(any());
        verifyNoMoreInteractions(repository);
    }
}

