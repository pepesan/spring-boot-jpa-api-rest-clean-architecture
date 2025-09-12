package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.config;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

class HttpClientsConfigUnitTest {

    @Test
    void productosClientConstruidoManualmenteUsaLaBaseUrl() throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            server.start();
            String base = server.url("/").toString();
            if (base.endsWith("/")) base = base.substring(0, base.length() - 1);

            var config = new HttpClientsConfig();
            WebClient client = config.productosClient(WebClient.builder(), base);

            server.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .setBody("{\"ok\":true}")
                    .addHeader("Content-Type", "application/json"));

            String resp = client.get()
                    .uri("/productos")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            assertThat(resp).contains("\"ok\":true");
        }
    }
}
