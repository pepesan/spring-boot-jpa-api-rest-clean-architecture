package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsert;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;

public interface AddProductoUseCase {
    ProductoView add(ProductoInsert producto);
}
