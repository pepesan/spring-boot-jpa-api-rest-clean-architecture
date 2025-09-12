package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.out.ProductosRemoteUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ConsultarProductosClienteInteractorTest {

    @Mock
    ProductosRemoteUseCase productosRemote;

    @InjectMocks
    ConsultarProductosClienteInteractor interactor;

    @Test
    void ejecutar_delega_en_port_out_y_devuelve_lista() {
        // given
        var esperado = List.of(
                new ProductoView(1L, "Teclado", 79.9),
                new ProductoView(2L, "Ratón", 39.5)
        );
        given(productosRemote.listarProductos()).willReturn(esperado);

        // when
        var resultado = interactor.ejecutar();

        // then
        assertThat(resultado).isEqualTo(esperado);
        then(productosRemote).should().listarProductos();
        then(productosRemote).shouldHaveNoMoreInteractions();
    }

    @Test
    void ejecutar_propagacion_de_errores_del_port_out() {
        // given
        given(productosRemote.listarProductos())
                .willThrow(new RuntimeException("Fallo remoto"));

        // when / then
        assertThatThrownBy(() -> interactor.ejecutar())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Fallo remoto");
    }
}

