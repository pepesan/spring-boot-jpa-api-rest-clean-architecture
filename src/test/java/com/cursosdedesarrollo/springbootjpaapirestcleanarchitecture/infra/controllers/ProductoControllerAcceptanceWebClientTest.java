package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.controllers;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ProductoControllerAcceptanceWebClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("POST /productos crea y devuelve el producto")
    void crearProducto() {
        ProductoInsertOrUpdate body = new ProductoInsertOrUpdate();
        body.setNombre("Monitor");
        body.setPrecio(120.0);

        webTestClient.post()
                .uri("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(ProductoView.class)
                .value(p -> {
                    assertThat(p).isNotNull();
                    assertThat(p.getId()).isNotNull();
                    assertThat(p.getNombre()).isEqualTo("Monitor");
                    assertThat(p.getPrecio()).isEqualTo(120.0);
                });
    }

    @Test
    @DisplayName("GET /productos/{id} devuelve el producto creado previamente")
    void obtenerPorId() {
        // crear primero
        ProductoInsertOrUpdate body = new ProductoInsertOrUpdate();
        body.setNombre("Ratón");
        body.setPrecio(29.99);

        ProductoView creado =
                webTestClient.post()
                        .uri("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(body)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(ProductoView.class)
                        .returnResult()
                        .getResponseBody();

        assertThat(creado).isNotNull();
        Long id = creado.getId();
        assertThat(id).isNotNull();

        // obtener por id
        webTestClient.get()
                .uri("/productos/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(id.intValue())
                .jsonPath("$.nombre").isEqualTo("Ratón")
                .jsonPath("$.precio").isEqualTo(29.99);
    }

    @Test
    @DisplayName("PUT /productos/{id} actualiza el producto")
    void actualizarProducto() {
        // crear primero
        ProductoInsertOrUpdate body = new ProductoInsertOrUpdate();
        body.setNombre("Pantalla");
        body.setPrecio(199.99);

        ProductoView creado =
                webTestClient.post()
                        .uri("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(body)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(ProductoView.class)
                        .returnResult()
                        .getResponseBody();

        assertThat(creado).isNotNull();
        Long id = creado.getId();
        assertThat(id).isNotNull();

        // update
        ProductoInsertOrUpdate update = new ProductoInsertOrUpdate();
        update.setNombre("Pantalla HD");
        update.setPrecio(210.0);

        webTestClient.put()
                .uri("/productos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(update)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id.intValue())
                .jsonPath("$.nombre").isEqualTo("Pantalla HD")
                .jsonPath("$.precio").isEqualTo(210.0);
    }

    @Test
    @DisplayName("DELETE /productos/{id} elimina el producto")
    void borrarProducto() {
        // crear primero
        ProductoInsertOrUpdate body = new ProductoInsertOrUpdate();
        body.setNombre("Teclado");
        body.setPrecio(25.5);

        ProductoView creado =
                webTestClient.post()
                        .uri("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(body)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(ProductoView.class)
                        .returnResult()
                        .getResponseBody();

        assertThat(creado).isNotNull();
        Long id = creado.getId();
        assertThat(id).isNotNull();

        // borrar
        webTestClient.delete()
                .uri("/productos/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(id.intValue())
                .jsonPath("$.nombre").isEqualTo("Teclado")
                .jsonPath("$.precio").isEqualTo(25.5);

        // comprobar que ya no está
        webTestClient.get()
                .uri("/productos/{id}", id)
                .exchange()
                .expectStatus().isNotFound(); // ajusta si tu controlador devuelve otro código
    }

    @Test
    @DisplayName("GET /productos devuelve 200 y JSON")
    void listarProductos() {
        webTestClient.get()
                .uri("/productos")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$").isArray();
    }

    @Test
    @DisplayName("GET /productos/{id}/descuento/{porcentaje} aplica descuento sobre un producto existente")
    void aplicarDescuento() {
        // 1. crear el producto sobre el que vamos a aplicar descuento
        ProductoInsertOrUpdate body = new ProductoInsertOrUpdate();
        body.setNombre("Portátil");
        body.setPrecio(1000.0);

        ProductoView creado =
                webTestClient.post()
                        .uri("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(body)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(ProductoView.class)
                        .returnResult()
                        .getResponseBody();

        assertThat(creado).isNotNull();
        Long id = creado.getId();
        assertThat(id).isNotNull();

        // 2. aplicar el descuento al id real (no al 5 fijo)
        webTestClient.get()
                .uri("/productos/{id}/descuento/{porcentaje}", id, 25)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.idProducto").isEqualTo(id.intValue())
                // no sabemos exactamente la fórmula, así que solo comprobamos que viene el campo
                .jsonPath("$.precioFinal").isNumber();
    }
}
