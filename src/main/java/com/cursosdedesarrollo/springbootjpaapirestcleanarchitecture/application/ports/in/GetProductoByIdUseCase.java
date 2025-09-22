package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;

public interface GetProductoByIdUseCase {
    ProductoView obtenerPorId(Long id);
}
