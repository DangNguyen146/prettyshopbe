package com.prettyshopbe.prettyshopbe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ProductColor")
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String color;
    private String description;

    public ProductColor(String color, String description) {
        this.color = color;
        this.description = description;
    }

     public ProductColor() {
    }


    public ProductColor(ProductColor productColor) {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
