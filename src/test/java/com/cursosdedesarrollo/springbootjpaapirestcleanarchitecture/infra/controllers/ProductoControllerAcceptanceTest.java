package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.controllers;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class ProductoControllerAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/productos";
    }

    @Test
    @DisplayName("POST /productos seguido de GET /productos/{id} devuelve lo creado")
    void crearYLuegoObtener() {
        // 1. Creamos el producto
        ProductoInsertOrUpdate body = new ProductoInsertOrUpdate();
        body.setNombre("Monitor");
        body.setPrecio(120.0);

        ResponseEntity<ProductoView> createResponse =
                restTemplate.postForEntity(baseUrl(), body, ProductoView.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        ProductoView creado = createResponse.getBody();
        assertThat(creado).isNotNull();
        assertThat(creado.getId()).isNotNull();
        assertThat(creado.getNombre()).isEqualTo("Monitor");
        assertThat(creado.getPrecio()).isEqualTo(120.0);

        // 2. Recuperamos por id
        Long id = creado.getId();
        ResponseEntity<ProductoView> getResponse =
                restTemplate.getForEntity(baseUrl() + "/" + id, ProductoView.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        ProductoView obtenido = getResponse.getBody();
        assertThat(obtenido).isNotNull();
        assertThat(obtenido.getId()).isEqualTo(id);
        assertThat(obtenido.getNombre()).isEqualTo("Monitor");
        assertThat(obtenido.getPrecio()).isEqualTo(120.0);
    }

    @Test
    @DisplayName("PUT /productos/{id} actualiza y devuelve el producto")
    void actualizarProducto() {
        // primero creamos uno
        ProductoInsertOrUpdate body = new ProductoInsertOrUpdate();
        body.setNombre("Pantalla");
        body.setPrecio(199.99);

        ProductoView creado = restTemplate
                .postForEntity(baseUrl(), body, ProductoView.class)
                .getBody();

        assertThat(creado).isNotNull();
        Long id = creado.getId();

        // ahora actualizamos
        ProductoInsertOrUpdate update = new ProductoInsertOrUpdate();
        update.setNombre("Pantalla HD");
        update.setPrecio(210.0);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductoInsertOrUpdate> request = new HttpEntity<>(update, headers);

        ResponseEntity<ProductoView> updateResponse =
                restTemplate.exchange(baseUrl() + "/" + id,
                        HttpMethod.PUT,
                        request,
                        ProductoView.class);

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        ProductoView actualizado = updateResponse.getBody();
        assertThat(actualizado).isNotNull();
        assertThat(actualizado.getId()).isEqualTo(id);
        assertThat(actualizado.getNombre()).isEqualTo("Pantalla HD");
        assertThat(actualizado.getPrecio()).isEqualTo(210.0);
    }

    @Test
    @DisplayName("DELETE /productos/{id} elimina y al pedirlo devuelve 404")
    void borrarProducto() {
        // creamos
        ProductoInsertOrUpdate body = new ProductoInsertOrUpdate();
        body.setNombre("Teclado");
        body.setPrecio(25.5);

        ProductoView creado = restTemplate
                .postForEntity(baseUrl(), body, ProductoView.class)
                .getBody();

        assertThat(creado).isNotNull();
        Long id = creado.getId();

        // borramos
        restTemplate.delete(baseUrl() + "/" + id);

        // comprobamos que ya no está
        ResponseEntity<String> getDeleted =
                restTemplate.getForEntity(baseUrl() + "/" + id, String.class);

        assertThat(getDeleted.getStatusCode().value())
                .isIn(HttpStatus.NOT_FOUND.value(), HttpStatus.BAD_REQUEST.value());
        // depende de cómo tengas manejadas las excepciones
    }

    @Test
    @DisplayName("GET /productos devuelve 200 y JSON")
    void listarProductos() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(baseUrl(), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isNotNull();
        assertThat(response.getHeaders().getContentType().toString())
                .startsWith("application/json");
    }

    @Test
    @DisplayName("GET /productos/{id} devuelve el producto correspondiente")
    void obtenerProductoPorId() {
        // 1. Crear un producto previamente
        ProductoInsertOrUpdate body = new ProductoInsertOrUpdate();
        body.setNombre("Ratón");
        body.setPrecio(29.99);

        ProductoView creado = restTemplate
                .postForEntity(baseUrl(), body, ProductoView.class)
                .getBody();

        assertThat(creado).isNotNull();
        Long id = creado.getId();
        assertThat(id).isNotNull();

        // 2. Obtenerlo por ID mediante una petición HTTP real
        ResponseEntity<ProductoView> response =
                restTemplate.getForEntity(baseUrl() + "/" + id, ProductoView.class);

        // 3. Validar respuesta HTTP y contenido
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ProductoView obtenido = response.getBody();
        assertThat(obtenido).isNotNull();
        assertThat(obtenido.getId()).isEqualTo(id);
        assertThat(obtenido.getNombre()).isEqualTo("Ratón");
        assertThat(obtenido.getPrecio()).isEqualTo(29.99);
    }

}
