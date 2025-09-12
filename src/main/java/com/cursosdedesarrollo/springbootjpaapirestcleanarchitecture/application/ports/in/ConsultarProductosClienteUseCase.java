package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;

import java.util.List;


public interface ConsultarProductosClienteUseCase {
    List<ProductoView> ejecutar();
}
