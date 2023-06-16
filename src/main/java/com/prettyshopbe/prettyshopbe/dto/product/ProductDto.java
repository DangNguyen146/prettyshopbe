package com.prettyshopbe.prettyshopbe.dto.product;

import com.prettyshopbe.prettyshopbe.model.Product;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductDto {

    private Integer id;
    private @NotNull String name;
    private String imageURL;
    private double price;
    private String description;
    private @NotNull Integer categoryId;

    private List<String> size;

    private List<Integer> quantityBySizes;
    private Integer accessCount;


    public ProductDto(@NotNull String name, @NotNull String imageURL, @NotNull double price, @NotNull String description, @NotNull Integer categoryId, List<String> size, List<Integer> quantityBySizes) {
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;

        this.size = size;
        this.quantityBySizes = quantityBySizes;
        this.accessCount = accessCount;
    }

    public ProductDto(Product product) {
        this.setId(product.getId());
        this.setName(product.getName());
        this.setImageURL(product.getImageURL());
        this.setDescription(product.getDescription());
        this.setPrice(product.getPrice());
        this.setCategoryId(product.getCategory().getId());

        this.setSize(product.getSize());
        this.setQuantityBySizes(product.getQuantityBySizes());
        this.setAccessCount(product.getAccessCount());
    }

    public Integer getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public List<Integer> getQuantityBySizes() {
        return quantityBySizes;
    }

    public void setQuantityBySizes(List<Integer> quantityBySizes) {
        this.quantityBySizes = quantityBySizes;
    }
}
