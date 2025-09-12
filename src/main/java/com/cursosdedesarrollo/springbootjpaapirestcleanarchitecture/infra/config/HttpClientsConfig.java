package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HttpClientsConfig {
    @Bean(name = "productosClient")
    public WebClient productosClient(
            WebClient.Builder builder,
            @Value("${app.productos.base-url:http://localhost:8080}") String baseUrl
    ) {
        return builder
                .baseUrl(baseUrl)
                .build();
    }
}

