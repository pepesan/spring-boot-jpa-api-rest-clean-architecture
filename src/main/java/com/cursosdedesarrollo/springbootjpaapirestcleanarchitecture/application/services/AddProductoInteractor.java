package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoInput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoOutput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsert;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddProductoUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AplicarDescuentoUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models.Producto;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddProductoInteractor implements AddProductoUseCase {

    private final ProductoRepository repository;

    @Autowired
    public AddProductoInteractor(ProductoRepository repository) {
        this.repository = repository;
    }

    public AplicarDescuentoOutput ejecutar(AplicarDescuentoInput input) {
        Producto producto = repository.findById(input.idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        producto.aplicarDescuento(input.porcentaje);

        repository.guardar(producto);

        return new AplicarDescuentoOutput(producto.getId(), producto.getPrecio());
    }

    @Override
    public ProductoView add(ProductoInsert producto) {
        Producto p = new Producto();
        p.setNombre(producto.getNombre());
        p.setPrecio(producto.getPrecio());
        p = repository.guardar(p);

        return new ProductoView(p.getId(), p.getNombre(), p.getPrecio());
    }
}