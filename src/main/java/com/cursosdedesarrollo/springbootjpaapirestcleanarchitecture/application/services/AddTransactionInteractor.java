package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.InventarioInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.InventarioView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddInventarioUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddProductoUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddTransactionUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.DeleteProductoByIdUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.InventoryRepository;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;

public class AddTransactionInteractor implements AddTransactionUseCase {
    private final AddProductoUseCase addProductoUseCase;
    private final AddInventarioUseCase addInventarioUseCase;
    private final DeleteProductoByIdUseCase deleteProductoByIdUseCase;

    public AddTransactionInteractor(
            AddProductoUseCase addProductoUseCase,
            AddInventarioUseCase addInventarioUseCase,
            ProductoRepository productoRepository,
            InventoryRepository inventoryRepository,
            DeleteProductoByIdUseCase deleteProductoByIdUseCase){
        this.addProductoUseCase = addProductoUseCase;
        this.addInventarioUseCase = addInventarioUseCase;
        this.deleteProductoByIdUseCase = deleteProductoByIdUseCase;
    }

    public ProductoView addTransaction(ProductoInsertOrUpdate producto) {
        ProductoView productoGuardado = null;
        productoGuardado = addProductoUseCase.add(producto);
        addInventarioUseCase.add(
                    new InventarioInsertOrUpdate(productoGuardado.getId(), 1));

        return productoGuardado;
    }
}
