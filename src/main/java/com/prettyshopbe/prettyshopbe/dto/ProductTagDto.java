package com.prettyshopbe.prettyshopbe.dto;

public class ProductTagDto {
    private Integer id;
    private String tagName;
    private String tagDescription;

    public ProductTagDto(Integer id, String tagName, String tagDescription) {
        this.id = id;
        this.tagName = tagName;
        this.tagDescription = tagDescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }
}

