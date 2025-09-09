package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.controllers;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoInput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoOutput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AplicarDescuentoUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.ListarProductosQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final AplicarDescuentoUseCase aplicarDescuento;
    private final ListarProductosQuery listarProductos;

    @Autowired
    public ProductoController(AplicarDescuentoUseCase aplicarDescuento, ListarProductosQuery listarProductos) {
        this.aplicarDescuento = aplicarDescuento;
        this.listarProductos = listarProductos;
    }

    @GetMapping
    public ResponseEntity<List<ProductoView>> listar() {
        return ResponseEntity.ok(listarProductos.listar());
    }

    @PostMapping("/{id}/descuento/{porcentaje}")
    public ResponseEntity<AplicarDescuentoOutput> aplicar(
            @PathVariable String id,@PathVariable double porcentaje) {

        var input = new AplicarDescuentoInput();
        input.idProducto = id;
        input.porcentaje = porcentaje;

        var output = aplicarDescuento.ejecutar(input);
        return ResponseEntity.ok(output);
    }
}
