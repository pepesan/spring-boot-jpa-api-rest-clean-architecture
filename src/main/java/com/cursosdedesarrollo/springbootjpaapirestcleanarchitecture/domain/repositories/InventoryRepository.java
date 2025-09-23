package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Inventory;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    Optional<Inventory> findById(Long id);
    Inventory guardar(Inventory inventory);
    List<Inventory> findAll();
    Optional<Inventory> deleteById(Long id);
}
