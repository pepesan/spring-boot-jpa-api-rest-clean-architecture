package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.acceptance;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoOutput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsert;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test de aceptación end-to-end:
 * - Arranca la app en puerto aleatorio.
 * - Usa TestRestTemplate para hacer peticiones HTTP reales.
 * - Usa BD H2 en memoria con perfil "test".
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.DisplayName.class)
class ProductoControllerAcceptanceE2ETest {

    @LocalServerPort
    int port;

    private final TestRestTemplate rest = new TestRestTemplate();

    private String baseUrl() {
        return "http://localhost:" + port + "/productos";
    }

    @Test
    @DisplayName("1) POST /productos crea y devuelve ProductoView")
    void crearProducto_ok() {
        ProductoInsert body = new ProductoInsert("Teclado", 25.5);
        ResponseEntity<ProductoView> resp = rest.postForEntity(baseUrl(), body, ProductoView.class);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        ProductoView view = Objects.requireNonNull(resp.getBody());
        assertNotNull(view.getId());
        assertEquals("Teclado", view.getNombre());
        assertEquals(25.5, view.getPrecio(), 1e-9);
    }

    @Test
    @DisplayName("2) GET /productos lista lo creado")
    void listarProductos_ok() {
        // Arrange: crear 2 productos
        rest.postForEntity(baseUrl(), new ProductoInsert("Monitor", 120.0), ProductoView.class);
        rest.postForEntity(baseUrl(), new ProductoInsert("Ratón", 15.0), ProductoView.class);

        // Act
        ResponseEntity<ProductoView[]> resp = rest.getForEntity(baseUrl(), ProductoView[].class);

        // Assert
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        ProductoView[] arr = Objects.requireNonNull(resp.getBody());
        List<ProductoView> lista = Arrays.asList(arr);

        assertTrue(lista.size() >= 2);
        assertTrue(lista.stream().anyMatch(p -> "Monitor".equals(p.getNombre()) && p.getPrecio() == 120.0));
        assertTrue(lista.stream().anyMatch(p -> "Ratón".equals(p.getNombre()) && p.getPrecio() == 15.0));
    }

    @Test
    @DisplayName("3) POST /productos/{id}/descuento/{porcentaje} aplica y persiste el descuento")
    void aplicarDescuento_ok() {
        // Arrange: crear producto con precio 200.0
        ProductoView creado = rest.postForEntity(baseUrl(), new ProductoInsert("Pantalla", 200.0), ProductoView.class).getBody();
        assertNotNull(creado);
        Long id = creado.getId();

        // Act: aplicar 25% de descuento
        ResponseEntity<AplicarDescuentoOutput> respDesc = rest.getForEntity(
                baseUrl() + "/" + id + "/descuento/25",
                AplicarDescuentoOutput.class
        );

        // Assert: respuesta del endpoint
        assertEquals(HttpStatus.OK, respDesc.getStatusCode());
        AplicarDescuentoOutput out = Objects.requireNonNull(respDesc.getBody());
        assertEquals(id, out.idProducto);
        assertEquals(150.0, out.precioFinal, 1e-9);

        // Verificar que queda persistido (relistar y buscar el producto)
        ResponseEntity<ProductoView[]> respLista = rest.getForEntity(baseUrl(), ProductoView[].class);
        List<ProductoView> lista = Arrays.asList(Objects.requireNonNull(respLista.getBody()));

        ProductoView actualizado = lista.stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst()
                .orElseThrow();

        assertEquals(150.0, actualizado.getPrecio(), 1e-9);
    }

    // Utilidad: POST con headers si lo necesitas
    private <T> ResponseEntity<T> postJson(String url, Object body, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return rest.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), responseType);
    }
}

