package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos;

public class InventarioView {
    private Long id;
    private int quantity;

    public InventarioView() { }

    public InventarioView(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }
    public int getQuantity() { return quantity;}
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
