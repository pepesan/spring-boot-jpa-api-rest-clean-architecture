package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class InventoryEntity {

    @Id
    private Long id;

    private int quantity;

    // Constructor vacío para JPA
    public InventoryEntity() {}

    public InventoryEntity(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
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
