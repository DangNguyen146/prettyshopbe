package com.prettyshopbe.prettyshopbe.model;

import java.util.Date;
import java.util.Optional;

import com.prettyshopbe.prettyshopbe.dto.product.ProductDto;
import jakarta.persistence.*;

@Entity
@Table(name = "Tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "producttag_id")
    private ProductTag productTag;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "created_date")
    private Date createdDate;

    public Tag(Integer id, ProductTag productTag, Product product, Date createdDate) {
        this.id = id;
        this.productTag = productTag;
        this.product = product;
        this.createdDate = createdDate;
    }

    public Tag(Integer id, ProductTag productTag, Product product) {
        this.id = id;
        this.productTag = productTag;
        this.product = product;
    }

    public Tag() {

    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Tag(ProductTag productTag, Product product) {
        this.productTag = productTag;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductTag getProducttag() {
        return productTag;
    }

    public void setProducttag(ProductTag productTag) {
        this.productTag = productTag;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
