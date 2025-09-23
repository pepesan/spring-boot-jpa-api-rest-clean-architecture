package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.repositories;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities.InventoryEntity;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataInventoryRepository extends JpaRepository<InventoryEntity, Long> {
}
