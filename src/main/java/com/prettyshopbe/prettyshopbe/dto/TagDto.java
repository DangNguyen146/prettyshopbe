package com.prettyshopbe.prettyshopbe.dto;

import com.prettyshopbe.prettyshopbe.dto.product.ProductDto;

public class TagDto {
    private Integer id;
    private Integer productTagId;
    private ProductDto product;

    public TagDto(Integer id, Integer productTagId, ProductDto product) {
        this.id = id;
        this.productTagId = productTagId;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductTagId() {
        return productTagId;
    }

    public void setProductTagId(Integer productTagId) {
        this.productTagId = productTagId;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }
}
