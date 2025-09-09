package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.repositores;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities.ProductoEntity;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.mappers.ProductoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductoRepositoryImpl implements ProductoRepository {

    private SpringDataProductoRepository jpaRepository;

    @Autowired
    public ProductoRepositoryImpl(SpringDataProductoRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Producto> buscarPorId(String id) {
        return jpaRepository.findById(id)
                .map(ProductoMapper::toDomain);
    }

    @Override
    public Producto guardar(Producto producto) {
        ProductoEntity entity = ProductoMapper.toEntity(producto);
        ProductoEntity saved = jpaRepository.save(entity);
        return ProductoMapper.toDomain(saved);
    }
}
