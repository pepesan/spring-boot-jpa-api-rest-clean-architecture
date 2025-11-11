package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.InventarioInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddInventarioUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddProductoUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.DeleteProductoByIdUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.InventoryRepository;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddTransactionInteractorTest {

    @Mock
    private AddProductoUseCase addProductoUseCase;

    @Mock
    private AddInventarioUseCase addInventarioUseCase;

    @Mock
    private DeleteProductoByIdUseCase deleteProductoByIdUseCase;

    // parámetros del constructor no usados por la lógica, pero necesarios para inyección
    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private AddTransactionInteractor interactor;

    @Test
    void addTransaction_delegaYCreaInventarioConCantidadCero() {
        ProductoInsertOrUpdate input = mock(ProductoInsertOrUpdate.class);
        ProductoView expected = new ProductoView(1L, "Teclado", 79.9);

        when(addProductoUseCase.add(input)).thenReturn(expected);

        ProductoView result = interactor.addTransaction(input);

        assertSame(expected, result);
        verify(addProductoUseCase).add(input);

        ArgumentCaptor<InventarioInsertOrUpdate> captor = ArgumentCaptor.forClass(InventarioInsertOrUpdate.class);
        verify(addInventarioUseCase).add(captor.capture());
        InventarioInsertOrUpdate enviado = captor.getValue();

        assertEquals(expected.getId(), enviado.getId());
        // assertEquals(0, enviado.getQuantity());

        verifyNoMoreInteractions(addProductoUseCase, addInventarioUseCase, deleteProductoByIdUseCase, productoRepository, inventoryRepository);
    }

    @Test
    void addTransaction_siAddProductoLanzaExcepcion_noCreaInventarioYPropaga() {
        ProductoInsertOrUpdate input = mock(ProductoInsertOrUpdate.class);
        when(addProductoUseCase.add(input)).thenThrow(new RuntimeException("error persistencia"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> interactor.addTransaction(input));
        assertEquals("error persistencia", ex.getMessage());

        verify(addProductoUseCase).add(input);
        verifyNoInteractions(addInventarioUseCase);
    }
}