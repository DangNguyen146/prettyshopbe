package com.prettyshopbe.prettyshopbe.dto;

import com.prettyshopbe.prettyshopbe.dto.product.ProductDto;

public class ProductColorDto {
    private Integer id;
    private String colorName;
    private String colorDescription;

    public ProductColorDto(Integer id, String colorName, String colorDescription) {
        this.id = id;
        this.colorName = colorName;
        this.colorDescription = colorDescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorDescription() {
        return colorDescription;
    }

    public void setColorDescription(String colorDescription) {
        this.colorDescription = colorDescription;
    }
}
