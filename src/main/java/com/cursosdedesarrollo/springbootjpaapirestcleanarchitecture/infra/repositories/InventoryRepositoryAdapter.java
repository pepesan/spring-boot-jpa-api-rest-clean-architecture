package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.repositories;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Inventory;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.InventoryRepository;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities.InventoryEntity;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.mappers.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InventoryRepositoryAdapter implements InventoryRepository {

    private SpringDataInventoryRepository jpaRepository;

    @Autowired
    public InventoryRepositoryAdapter(SpringDataInventoryRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Inventory> findById(Long id) {
        return jpaRepository.findById(id)
                .map(InventoryMapper::toDomain);
    }

    @Override
    public Inventory guardar(Inventory inventory) {
        InventoryEntity entity = InventoryMapper.toEntity(inventory);
        InventoryEntity saved = jpaRepository.save(entity);
        return InventoryMapper.toDomain(saved);
    }

    @Override
    public List<Inventory> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(InventoryMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Inventory> deleteById(Long id) {
        Optional<InventoryEntity> inventoryBBDD =  jpaRepository.findById(id);
        inventoryBBDD.ifPresent(jpaRepository::delete);
        return inventoryBBDD.map(InventoryMapper::toDomain);
    }
}
