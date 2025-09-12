package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.clients;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductosRemoteHttpAdapterTest {

    static MockWebServer server;
    ProductosRemoteHttpAdapter adapter;

    @BeforeAll
    static void start() throws Exception {
        server = new MockWebServer();
        server.start();
    }

    @AfterAll
    static void stop() throws Exception {
        server.shutdown();
    }

    @BeforeEach
    void setUp() {
        // Base URL del WebClient apuntando al MockWebServer
        String base = server.url("/").toString();
        if (base.endsWith("/")) base = base.substring(0, base.length() - 1);

        WebClient client = WebClient.builder()
                .baseUrl(base)
                .build();

        adapter = new ProductosRemoteHttpAdapter(client);
    }

    @Test
    void listarProductos_ok_devuelveLista() {
        // given
        String body = """
            [
              {"id":"1","nombre":"Teclado","precio":79.9},
              {"id":"2","nombre":"Raton","precio":39.5}
            ]
            """;
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(body));

        // when
        List<ProductoView> res = adapter.listarProductos();

        // then
        assertThat(res).hasSize(2);
        assertThat(res.get(0).getId()).isEqualTo(1L);
        assertThat(res.get(0).getNombre()).isEqualTo("Teclado");
        assertThat(res.get(0).getPrecio()).isEqualTo(79.9);
    }

    @Test
    void listarProductos_404_lanzaRuntimeExceptionConMensajeGenerico() {
        // given
        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .addHeader("Content-Type", "text/plain")
                .setBody("Not Found"));

        // when / then
        assertThatThrownBy(() -> adapter.listarProductos())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Fallo remoto /productos");
        // Nota: el adaptador mapea el error en onStatus y luego lo envuelve en el catch genérico.
    }

    @Test
    void listarProductos_500_lanzaRuntimeException() {
        // given
        server.enqueue(new MockResponse()
                .setResponseCode(500)
                .addHeader("Content-Type", "text/plain")
                .setBody("Internal Error"));

        // when / then
        assertThatThrownBy(() -> adapter.listarProductos())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Fallo remoto /productos");
    }

    @Test
    void listarProductos_timeout_lanzaRuntimeException() {
        // given: simulamos respuesta lenta (>3s) para gatillar el .timeout(Duration.ofSeconds(3))
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody("[]")
                .setBodyDelay(5, TimeUnit.SECONDS));

        // when / then
        assertThatThrownBy(() -> adapter.listarProductos())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Fallo remoto /productos");
    }
}

