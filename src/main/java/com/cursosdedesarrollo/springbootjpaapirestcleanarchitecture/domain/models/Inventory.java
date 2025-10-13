package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.domain.models;

public class Inventory {
    private Long id;
    private int quantity;

    public Inventory() {}

    public Inventory(Long id, int quantity) {
        this.id = id;
        if (this.getId() == null || this.getId() <= 0)
            throw new IllegalArgumentException("El id debe existir y ser mayor que 0");
        this.quantity = quantity;
        if (this.getQuantity() <= 0)
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
