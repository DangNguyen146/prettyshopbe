package com.prettyshopbe.prettyshopbe.model;

public class ProductSize {

    private String size;
    private int quantity;

    public ProductSize(String size, int quantity) {
        this.size = size;
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
