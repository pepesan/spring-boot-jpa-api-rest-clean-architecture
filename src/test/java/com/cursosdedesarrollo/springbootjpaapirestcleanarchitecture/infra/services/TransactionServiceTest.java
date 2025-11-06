package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddTransactionUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    AddTransactionUseCase addTransactionUseCase;

    @InjectMocks
    TransactionService transactionService;

    @Test
    void addTransaction_delegaYDevuelve() {
        // dato esperado
        ProductoView expected = new ProductoView(1L, "Teclado", 79.9);

        // entrada ficticia (no requiere conocer el constructor)
        ProductoInsertOrUpdate input = mock(ProductoInsertOrUpdate.class);

        // comportamiento del mock
        given(addTransactionUseCase.addTransaction(input)).willReturn(expected);

        // ejecución
        ProductoView result = transactionService.addTransaction(input);

        // comprobaciones
        assertSame(expected, result);
        verify(addTransactionUseCase).addTransaction(input);
    }
}
