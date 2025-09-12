package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.ConsultarProductosClienteUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.out.ProductosRemoteUseCase;


import java.util.List;

public class ConsultarProductosClienteInteractor implements ConsultarProductosClienteUseCase {

    private final ProductosRemoteUseCase productosRemote;

    public ConsultarProductosClienteInteractor(ProductosRemoteUseCase productosRemote) {
        this.productosRemote = productosRemote;
    }

    @Override
    public List<ProductoView> ejecutar() {
        return productosRemote.listarProductos();
    }
}
