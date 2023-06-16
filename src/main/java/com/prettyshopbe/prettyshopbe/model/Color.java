package com.prettyshopbe.prettyshopbe.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Color")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "productcolor_id")
    private ProductColor productColor;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "created_date")
    private Date createdDate;

    public Color(Integer id, ProductColor productColor, Product product, Date createdDate) {
        this.id = id;
        this.productColor = productColor;
        this.product = product;
        this.createdDate = createdDate;
    }

    public Color(Integer id, ProductColor productColor, Product product) {
        this.id = id;
        this.productColor = productColor;
        this.product = product;
    }

    public Color() {

    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Color(ProductColor productColor, Product product) {
        this.productColor = productColor;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductColor getProductcolor() {
        return productColor;
    }

    public void setProductcolor(ProductColor productColor) {
        this.productColor = productColor;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
