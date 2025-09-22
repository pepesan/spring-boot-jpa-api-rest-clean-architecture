package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.controllers;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoInput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoOutput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final AplicarDescuentoUseCase aplicarDescuento;
    private final ListarProductosUseCase listarProductos;
    private final AddProductoUseCase addProducto;
    private final GetProductoByIdUseCase getProductoByIdUseCase;
    private final UpdateProductoUseCase updateProductoUseCase;
    private final DeleteProductoByIdUseCase deleteProductoByIdUseCase;

    @Autowired
    public ProductoController(
            AplicarDescuentoUseCase aplicarDescuento,
            ListarProductosUseCase listarProductos,
            AddProductoUseCase addProducto,
            GetProductoByIdUseCase getProductoByIdUseCase, UpdateProductoUseCase updateProductoUseCase, DeleteProductoByIdUseCase deleteProductoByIdUseCase)
    {
        this.aplicarDescuento = aplicarDescuento;
        this.listarProductos = listarProductos;
        this.addProducto = addProducto;
        this.getProductoByIdUseCase = getProductoByIdUseCase;
        this.updateProductoUseCase = updateProductoUseCase;
        this.deleteProductoByIdUseCase = deleteProductoByIdUseCase;
    }

    @GetMapping
    public ResponseEntity<List<ProductoView>> listar() {
        return ResponseEntity.ok(listarProductos.listar());
    }

    @PostMapping
    public ResponseEntity<ProductoView> crear(@RequestBody ProductoInsertOrUpdate producto) {
        // Lógica para crear un producto (no implementada en este ejemplo)
        return ResponseEntity.ok(addProducto.add(producto));
    }

    @GetMapping("/{id}/descuento/{porcentaje}")
    public ResponseEntity<AplicarDescuentoOutput> aplicar(
            @PathVariable Long id,@PathVariable double porcentaje) {

        var input = new AplicarDescuentoInput();
        input.idProducto = id;
        input.porcentaje = porcentaje;

        var output = aplicarDescuento.ejecutar(input);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoView> obtenerPorId(@PathVariable Long id) {
        ProductoView producto = getProductoByIdUseCase.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductoView> actualizarProducto(
            @PathVariable Long id,
            @RequestBody ProductoInsertOrUpdate update) {
        ProductoView actualizado = updateProductoUseCase.update(id, update);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoView> deletePorId(@PathVariable Long id) {
        ProductoView producto = deleteProductoByIdUseCase.borrarPorId(id);
        return ResponseEntity.ok(producto);
    }
}

