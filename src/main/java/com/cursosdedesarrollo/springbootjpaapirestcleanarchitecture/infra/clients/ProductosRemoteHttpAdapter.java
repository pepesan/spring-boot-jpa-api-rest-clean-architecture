package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.clients;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.out.ProductosRemoteUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.List;

@Component
public class ProductosRemoteHttpAdapter implements ProductosRemoteUseCase {

    private final WebClient productosClient;

    public ProductosRemoteHttpAdapter(@Qualifier("productosClient") WebClient productosClient) {
        this.productosClient = productosClient;
    }

    @Override
    public List<ProductoView> listarProductos() {
        try {
            return productosClient.get()
                    .uri("/productos")
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, resp ->
                            resp.bodyToMono(String.class)
                                    .map(body -> new RuntimeException(
                                            "Error remoto " + resp.statusCode() + ": " + body)))
                    // Opción A: usar ParameterizedTypeReference
                    .bodyToMono(new ParameterizedTypeReference<List<ProductoView>>() {})
                    // (alternativa robusta)
                    // .bodyToFlux(ProductoView.class).collectList()
                    .timeout(Duration.ofSeconds(3))
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Fallo remoto /productos: "
                    + e.getStatusCode() + " " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Fallo remoto /productos", e);
        }
    }
}

