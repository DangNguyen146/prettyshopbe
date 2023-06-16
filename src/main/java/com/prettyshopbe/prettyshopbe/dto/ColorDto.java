package com.prettyshopbe.prettyshopbe.dto;


import com.prettyshopbe.prettyshopbe.dto.product.ProductDto;

public class ColorDto {
    private Integer id;
    private Integer productColorId;
    private ProductDto product;

    public ColorDto(Integer id, Integer productColorId, ProductDto product) {
        this.id = id;
        this.productColorId = productColorId;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductColorId() {
        return productColorId;
    }

    public void setProductColorId(Integer productColorId) {
        this.productColorId = productColorId;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }
}
