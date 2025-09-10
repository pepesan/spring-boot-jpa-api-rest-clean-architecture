Feature: ProductoView DTO
  Como desarrollador
  Quiero validar el comportamiento del DTO ProductoView
  Para asegurar que constructores, getters y setters funcionan correctamente

  Scenario: Constructor vacío inicializa valores por defecto
    Given un ProductoView creado sin parámetros
    Then el id debe ser null
    And el nombre debe ser null
    And el precio debe ser 0.0

  Scenario: Constructor con parámetros asigna valores correctamente
    When creo un ProductoView con id 1, nombre "Teclado" y precio 25.5
    Then el id debe ser 1
    And el nombre debe ser "Teclado"
    And el precio debe ser 25.5

  Scenario: Setters actualizan los valores y getters los devuelven
    Given un ProductoView creado sin parámetros
    When asigno id 3, nombre "Monitor" y precio 199.99
    Then el id debe ser 3
    And el nombre debe ser "Monitor"
    And el precio debe ser 199.99
