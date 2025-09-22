package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;

public interface DeleteProductoByIdUseCase {
    ProductoView borrarPorId(Long id);
}
