package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.controllers;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.*;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AplicarDescuentoUseCase aplicarDescuento;

    @MockitoBean
    private ListarProductosUseCase listarProductos;

    @MockitoBean
    private AddProductoUseCase addProducto;

    @MockitoBean
    GetProductoByIdUseCase getProductoByIdUseCase;

    @MockitoBean
    UpdateProductoUseCase   updateProductoUseCase;

    @MockitoBean
    DeleteProductoByIdUseCase   deleteProductoByIdUseCase;

    @Test
    @DisplayName("GET /productos devuelve lista de productos")
    void listarProductos() throws Exception {
        ProductoView p1 = new ProductoView(1L, "Teclado", 25.5);
        ProductoView p2 = new ProductoView(2L, "Ratón", 15.0);
        when(listarProductos.listar()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/productos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Teclado")))
                .andExpect(jsonPath("$[0].precio", is(25.5)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nombre", is("Ratón")))
                .andExpect(jsonPath("$[1].precio", is(15.0)));

        verify(listarProductos, times(1)).listar();
        verifyNoMoreInteractions(listarProductos);
    }

    @Test
    @DisplayName("POST /productos crea un producto")
    void crearProducto() throws Exception {
        ProductoInsertOrUpdate input = new ProductoInsertOrUpdate();
        input.setNombre("Monitor");
        input.setPrecio(120.0);

        ProductoView expected = new ProductoView(10L, "Monitor", 120.0);
        when(addProducto.add(any(ProductoInsertOrUpdate.class))).thenReturn(expected);

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.nombre", is("Monitor")))
                .andExpect(jsonPath("$.precio", is(120.0)));

        verify(addProducto, times(1)).add(any(ProductoInsertOrUpdate.class));
        verifyNoMoreInteractions(addProducto);
    }

    @Test
    @DisplayName("GET /productos/{id}/descuento/{porcentaje} aplica descuento")
    void aplicarDescuentoProducto() throws Exception {
        AplicarDescuentoOutput expected = new AplicarDescuentoOutput();
        expected.idProducto = 5L;
        expected.precioFinal = 150.0;

        when(aplicarDescuento.ejecutar(any(AplicarDescuentoInput.class)))
                .thenReturn(expected);

        mockMvc.perform(get("/productos/5/descuento/25")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto", is(5)))
                .andExpect(jsonPath("$.precioFinal", is(150.0)));

        verify(aplicarDescuento, times(1))
                .ejecutar(any(AplicarDescuentoInput.class));
        verifyNoMoreInteractions(aplicarDescuento);
    }

    @Test
    void devuelveProductoPorId() throws Exception {
        ProductoView producto = new ProductoView(5L, "Pantalla", 199.99);
        given(getProductoByIdUseCase.obtenerPorId(5L)).willReturn(producto);

        mockMvc.perform(get("/productos/5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.nombre").value("Pantalla"))
                .andExpect(jsonPath("$.precio").value(199.99));
    }

    @Test
    void actualizarProducto() throws Exception {
        ProductoInsertOrUpdate update = new ProductoInsertOrUpdate();
        update.setNombre("Pantalla");
        update.setPrecio(199.99);

        ProductoView actualizado = new ProductoView(6L, "Pantalla", 199.99);
        when(updateProductoUseCase.update(eq(6L), any(ProductoInsertOrUpdate.class)))
                .thenReturn(actualizado);

        mockMvc.perform(put("/productos/6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(6L))
                .andExpect(jsonPath("$.nombre").value("Pantalla"))
                .andExpect(jsonPath("$.precio").value(199.99));
    }

    @Test
    void borrarProductoPorId() throws Exception {
        ProductoView producto = new ProductoView(7L, "Teclado", 25.5);
        when(deleteProductoByIdUseCase.borrarPorId(7L)).thenReturn(producto);

        mockMvc.perform(delete("/productos/7"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.nombre").value("Teclado"))
                .andExpect(jsonPath("$.precio").value(25.5));
    }

}

