package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.repositories;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        String base = server.url("/").toString();
        if (base.endsWith("/")) base = base.substring(0, base.length() - 1);

        WebClient client = WebClient.builder()
                .baseUrl(base)
                .build();

        adapter = new ProductosRemoteHttpAdapter(client);
    }

    @Test
    void listarProductos_ok_devuelveLista() {
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

        List<ProductoView> res = adapter.listarProductos();

        assertThat(res).hasSize(2);
        assertThat(res.getFirst().getId()).isEqualTo(1L);
        assertThat(res.getFirst().getNombre()).isEqualTo("Teclado");
        assertThat(res.getFirst().getPrecio()).isEqualTo(79.9);
        assertThat(res.get(1).getId()).isEqualTo(2L);
        assertThat(res.get(1).getNombre()).isEqualTo("Raton");
        assertThat(res.get(1).getPrecio()).isEqualTo(39.5);
    }

    @Test
    void listarProductos_404_lanzaRuntimeExceptionConMensajeGenerico() {
        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .addHeader("Content-Type", "text/plain")
                .setBody("Not Found"));

        assertThatThrownBy(() -> adapter.listarProductos())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Fallo remoto /productos");
    }

    @Test
    void listarProductos_500_lanzaRuntimeException() {
        server.enqueue(new MockResponse()
                .setResponseCode(500)
                .addHeader("Content-Type", "text/plain")
                .setBody("Internal Error"));

        assertThatThrownBy(() -> adapter.listarProductos())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Fallo remoto /productos");
    }

    @Test
    void listarProductos_timeout_lanzaRuntimeException() {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody("[]")
                .setBodyDelay(5, TimeUnit.SECONDS));

        assertThatThrownBy(() -> adapter.listarProductos())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Fallo remoto /productos");
    }

    @Test
    void listarProductos_webClientResponseException_sePropagaComoRuntimeConDetalle() {
        WebClient mockClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec<?> reqUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> reqHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec respSpec = mock(WebClient.ResponseSpec.class);

        // usar doReturn(...) para evitar problemas de inferencia de tipos en métodos genéricos encadenados
        doReturn(reqUriSpec).when(mockClient).get();
        doReturn(reqHeadersSpec).when(reqUriSpec).uri("/productos");
        doReturn(respSpec).when(reqHeadersSpec).retrieve();

        WebClientResponseException wcre = WebClientResponseException.create(
                404,
                "Not Found",
                HttpHeaders.EMPTY,
                "Not Found".getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8
        );

        when(respSpec.bodyToMono(any(ParameterizedTypeReference.class))).thenThrow(wcre);

        ProductosRemoteHttpAdapter adapterWithMock = new ProductosRemoteHttpAdapter(mockClient);

        assertThatThrownBy(() -> adapterWithMock.listarProductos())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Fallo remoto /productos");
    }
}