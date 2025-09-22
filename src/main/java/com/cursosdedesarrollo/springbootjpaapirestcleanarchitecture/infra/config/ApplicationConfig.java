package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.config;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.GetProductoByIdUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.out.ProductosRemoteUseCase;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.services.*;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.repositories.ProductoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public AplicarDescuentoInteractor aplicarDescuentoInteractor(ProductoRepository repository) {
        return new AplicarDescuentoInteractor(repository);
    }
    @Bean
    public AddProductoInteractor addProductoInteractor(ProductoRepository repository) {
        return new AddProductoInteractor(repository);
    }
    @Bean
    public ListarProductosInteractor listarProductosInteractor(ProductoRepository repository) {
        return new ListarProductosInteractor(repository);
    }
    @Bean
    public ConsultarProductosClienteInteractor ConsultarProductosClienteInteractor(ProductosRemoteUseCase useCase) {
        return new ConsultarProductosClienteInteractor(useCase);
    }
    @Bean
    public GetProductoByIdInteractor getProductoByIdInteractor(ProductoRepository repository) {
        return new GetProductoByIdInteractor(repository);
    }


}
