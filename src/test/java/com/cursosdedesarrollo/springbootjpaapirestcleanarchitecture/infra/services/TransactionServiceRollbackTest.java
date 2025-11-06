package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddInventarioUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class TransactionServiceRollbackTest {

    @Autowired
    TransactionService transactionService;

    @Autowired
    ProductoRepository productoRepository;

    /**
     * Desactiva la transacción del test para que la transacción del servicio
     * sea la que controle commit/rollback.
     */
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void cuandoHayExcepcion_debeHacerRollback() {
        int before = productoRepository.findAll().size();

        ProductoInsertOrUpdate input = new ProductoInsertOrUpdate("ProductoPrueba", 10.0);

        try {
            transactionService.addTransaction(input);
            fail("Se esperaba RuntimeException para forzar rollback");
        } catch (RuntimeException expected) {
            // excepción esperada
        }

        // Si hubo rollback, el número de registros no debe haber cambiado
        assertEquals(before, productoRepository.findAll().size());
    }

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