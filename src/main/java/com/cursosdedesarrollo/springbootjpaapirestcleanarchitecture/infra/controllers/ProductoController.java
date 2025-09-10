package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.controllers;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoInput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoOutput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsert;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddProductoUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AplicarDescuentoUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.ListarProductosUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final AplicarDescuentoUseCase aplicarDescuento;
    private final ListarProductosUseCase listarProductos;
    private final AddProductoUseCase addProducto;

    @Autowired
    public ProductoController(
            AplicarDescuentoUseCase aplicarDescuento,
            ListarProductosUseCase listarProductos,
            AddProductoUseCase addProducto)
    {
        this.aplicarDescuento = aplicarDescuento;
        this.listarProductos = listarProductos;
        this.addProducto = addProducto;
    }

    @GetMapping
    public ResponseEntity<List<ProductoView>> listar() {
        return ResponseEntity.ok(listarProductos.listar());
    }

    @PostMapping
    public ResponseEntity<ProductoView> crear(@RequestBody ProductoInsert producto) {
        // Lógica para crear un producto (no implementada en este ejemplo)
        return ResponseEntity.ok(addProducto.add(producto));
    }

    @PostMapping("/{id}/descuento/{porcentaje}")
    public ResponseEntity<AplicarDescuentoOutput> aplicar(
            @PathVariable Long id,@PathVariable double porcentaje) {

        var input = new AplicarDescuentoInput();
        input.idProducto = id;
        input.porcentaje = porcentaje;

        var output = aplicarDescuento.ejecutar(input);
        return ResponseEntity.ok(output);
    }
}
