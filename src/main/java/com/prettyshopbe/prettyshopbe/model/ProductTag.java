package com.prettyshopbe.prettyshopbe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ProductTag")
public class ProductTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tag;
    private String description;

     public ProductTag(ProductTag productTag) {
     }

    public ProductTag(Integer id, String tag, String description) {
        this.id = id;
        this.tag = tag;
        this.description = description;
    }

    public ProductTag(String tag) {
        this.tag = tag;
    }

    public ProductTag(String tag, String description) {
        this.tag = tag;
        this.description = description;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
