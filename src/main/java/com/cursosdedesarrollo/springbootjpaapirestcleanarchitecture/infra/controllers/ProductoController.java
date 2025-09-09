package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.controllers;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoInput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoOutput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AplicarDescuentoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final AplicarDescuentoUseCase aplicarDescuento;

    @Autowired
    public ProductoController(AplicarDescuentoUseCase aplicarDescuento) {
        this.aplicarDescuento = aplicarDescuento;
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
