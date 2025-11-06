package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.dtos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ErrorMessageTest {

    @Test
    void constructorYGetterDevuelveMensajeCorrecto() {
        ErrorMessage error = new ErrorMessage("Error de prueba");
        assertEquals("Error de prueba", error.getMensaje());
    }

    @Test
    void setterCambiaElMensaje() {
        ErrorMessage error = new ErrorMessage("Inicial");
        error.setMensaje("Modificado");
        assertEquals("Modificado", error.getMensaje());
    }
}
