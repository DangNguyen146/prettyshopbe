package com.prettyshopbe.prettyshopbe.dto.cart;

import jakarta.validation.constraints.NotNull;

import java.util.Map;


public class AddToCartDto {
    private Integer id;
    private @NotNull Integer productId;
    private @NotNull Integer quantity;
    private @NotNull Map<String, Integer> quantityBySizes;

    public AddToCartDto() {
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantityBySizes  +
                ",";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Map<String, Integer> getQuantityBySizes() {
        return quantityBySizes;
    }

    public void setQuantityBySizes(Map<String, Integer> quantityBySizes) {
        this.quantityBySizes = quantityBySizes;
    }
}
