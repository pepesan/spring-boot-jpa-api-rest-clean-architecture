package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.repositories;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataProductoRepository extends JpaRepository<ProductoEntity, Long> {
}
