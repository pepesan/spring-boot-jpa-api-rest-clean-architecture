package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.out;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;

import java.util.List;

public interface ProductosRemoteUseCase {
    List<ProductoView> listarProductos();
}
