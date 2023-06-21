package com.prettyshopbe.prettyshopbe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettyshopbe.prettyshopbe.dto.product.ProductDto;
import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private @NotNull String name;
    private String imageURL;
    private double price;
    private String description;

    // New fields for product size and quantity
    @ElementCollection
    private List<String> size;

    @ElementCollection
    private List<Integer> quantityBySizes;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    Category category;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<WishList> wishListList;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Cart> carts;

    // New field for access count
    private Integer accessCount = 0;

    @Column(name = "created_date")
    private Date createdDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    public Product(Integer id, String name, String imageURL, double price, String description, List<String> size, List<Integer> quantityBySizes, Category category, List<WishList> wishListList, List<Cart> carts, Integer accessCount, Date createdDate) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.description = description;
        this.size = size;
        this.quantityBySizes = quantityBySizes;
        this.category = category;
        this.wishListList = wishListList;
        this.carts = carts;
        this.accessCount = accessCount;
        this.createdDate = new Date();
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Product(ProductDto productDto, Category category) {
        this.id = productDto.getId();
        this.name = productDto.getName();
        this.imageURL = productDto.getImageURL();
        this.description = productDto.getDescription();
        this.price = productDto.getPrice();
        this.category = category;
        this.size = productDto.getSize(); // Initialize product size for this product entity
        this.quantityBySizes = productDto.getQuantityBySizes(); // Initialize product size quantity for this product
                                                                // entity
        this.accessCount = 0;
        this.createdDate = new Date();
    }



    public Product(String name, String imageURL, double price, String description, Category category, List<String> size, List<Integer> quantityBySizes) {
        super();
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.description = description;
        this.category = category;
        this.size = size;
        this.quantityBySizes = quantityBySizes;
        this.accessCount = 0;
        this.createdDate = new Date();
    }

    public Integer getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public List<WishList> getWishListList() {
        return wishListList;
    }

    public void setWishListList(List<WishList> wishListList) {
        this.wishListList = wishListList;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

}
