package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpringBootJpaApiRestCleanArchitectureApplicationMainTest {

    @Test
    @DisplayName("main(): arranca la aplicación sin lanzar excepciones")
    void main_ejecutaSinErrores() {
        // Act
        SpringBootJpaApiRestCleanArchitectureApplication.main(new String[] {});
        // Assert: si llega aquí, la línea se ha ejecutado y JaCoCo la contará
    }
}

