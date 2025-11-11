package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.controllers;


import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.out.ProductosRemoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ProductosRemoteUseCase listarProductos;

    @Autowired
    public ClienteController(ProductosRemoteUseCase listarProductos) {
        this.listarProductos = listarProductos;
    }

    @GetMapping
    public ResponseEntity<List<ProductoView>> obtenerProductosParaCliente() {
        List<ProductoView> productos = listarProductos.listarProductos();
        return ResponseEntity.ok(productos);
    }
}
