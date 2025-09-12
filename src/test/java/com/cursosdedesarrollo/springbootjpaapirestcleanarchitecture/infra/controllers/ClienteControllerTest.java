package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.controllers;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.out.ProductosRemoteUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.controllers.ClienteController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;// <-- reemplaza a @MockBean
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClienteController.class)
class ClienteControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    ProductosRemoteUseCase productosRemote; // <-- mock registrado en el contexto

    @Test
    void devuelveListaDeProductos() throws Exception {
        given(productosRemote.listarProductos()).willReturn(
                List.of(new ProductoView(1L,"Teclado",79.9))
        );

        mvc.perform(get("/cliente"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Teclado"))
                .andExpect(jsonPath("$[0].precio").value(79.9));
    }
}
