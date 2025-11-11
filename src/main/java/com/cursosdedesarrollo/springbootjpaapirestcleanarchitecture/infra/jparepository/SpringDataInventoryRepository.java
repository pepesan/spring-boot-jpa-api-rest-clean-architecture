package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.jparepository;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataInventoryRepository extends JpaRepository<InventoryEntity, Long> {
}
