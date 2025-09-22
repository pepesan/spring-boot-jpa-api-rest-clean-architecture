package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.advices;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.dto.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @Test
    void handleIllegalArgumentExceptionDevuelveNotFoundYMensaje() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        IllegalArgumentException ex = new IllegalArgumentException("Producto no encontrado");

        ResponseEntity<ErrorMessage> response = handler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Producto no encontrado", response.getBody().getMensaje());
    }
}
