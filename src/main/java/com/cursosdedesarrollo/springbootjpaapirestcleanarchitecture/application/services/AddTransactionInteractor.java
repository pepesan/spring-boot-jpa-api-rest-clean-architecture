package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.InventarioInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.InventarioView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddInventarioUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddProductoUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddTransactionUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.InventoryRepository;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;

public class AddTransactionInteractor implements AddTransactionUseCase {
    private final ProductoRepository productoRepository;
    private final InventoryRepository inventoryRepository;
    private final AddProductoUseCase addProductoUseCase;
    private final AddInventarioUseCase addInventarioUseCase;

    public AddTransactionInteractor(
            AddProductoUseCase addProductoUseCase,
            AddInventarioUseCase addInventarioUseCase,
            ProductoRepository productoRepository,
            InventoryRepository inventoryRepository){
        this.addProductoUseCase = addProductoUseCase;
        this.addInventarioUseCase = addInventarioUseCase;
        this.productoRepository = productoRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public ProductoView addTransaction(ProductoInsertOrUpdate producto) {
        ProductoView productoGuardado = null;
        InventarioView inventarioGuardado = null;
        try {
            productoGuardado = addProductoUseCase.add(producto);
            inventarioGuardado = this.addInventarioUseCase.add(new InventarioInsertOrUpdate(productoGuardado.getId(), 0));
        } catch (Exception e) {
            // Aquí deberías eliminar el producto si falla el inventario
            productoRepository.deleteById(productoGuardado.getId());
        }

        return productoGuardado;
    }
}
