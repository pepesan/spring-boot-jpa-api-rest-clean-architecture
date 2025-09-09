package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoInput;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.AplicarDescuentoOutput;

public interface AplicarDescuentoUseCase {
    AplicarDescuentoOutput ejecutar(AplicarDescuentoInput input);
}
