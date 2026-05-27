package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> home() {
        return ResponseEntity.ok(Map.of(
            "proyecto", "Spring Boot JPA API REST - Clean Architecture",
            "descripcion", "API REST de ejemplo que implementa arquitectura limpia (Clean Architecture) con Spring Boot 3, JPA e H2.",
            "tecnologias", List.of("Spring Boot 3.5", "Java 21", "JPA / Hibernate", "H2 (in-memory)", "Lombok", "Spring Actuator"),
            "endpoints", Map.of(
                "productos",   "GET|POST|PUT|DELETE /productos",
                "inventario",  "GET|POST /inventario",
                "transaccion", "POST /transaction",
                "cliente",     "GET /cliente/productos",
                "salud",       "GET /actuator/health",
                "h2-console",  "GET /h2-console"
            ),
            "status", "UP"
        ));
    }
}
