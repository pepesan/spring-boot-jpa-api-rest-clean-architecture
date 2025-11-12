package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddInventarioUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class TransactionServiceRollbackTest {

    @Autowired
    TransactionService transactionService;

    @Autowired
    ProductoRepository productoRepository;

    /**
     * Test configuration que inyecta un AddInventarioUseCase que falla
     * durante la operación, provocando una RuntimeException dentro de la transacción.
     *
     * ADAPTA si en tu contexto necesitas otro bean que lance la excepción
     * (p. ej. sobreescribir AddTransactionUseCase o AddInventarioUseCase según tu wiring).
     */
    @TestConfiguration
    static class FailingBeansConfig {

        @Bean
        AddInventarioUseCase failingAddInventario() {
            return inventario -> {
                throw new RuntimeException("falla intencional para probar rollback");
            };
        }
    }
}