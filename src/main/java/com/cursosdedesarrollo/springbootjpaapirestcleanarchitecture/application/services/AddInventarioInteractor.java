package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.InventarioInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.InventarioView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddInventarioUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddProductoUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Inventory;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.InventoryRepository;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;


public class AddInventarioInteractor implements AddInventarioUseCase {

    private final InventoryRepository repository;


    public AddInventarioInteractor(InventoryRepository repository) {
        this.repository = repository;
    }


    @Override
    public InventarioView add(InventarioInsertOrUpdate inventario) {
        Inventory inventory = new Inventory();
        inventory.setId(inventario.getId());
        inventory.setQuantity(inventario.getQuantity());
        inventory = repository.guardar(inventory);
        return new InventarioView(inventory.getId(), inventory.getQuantity());
    }
}