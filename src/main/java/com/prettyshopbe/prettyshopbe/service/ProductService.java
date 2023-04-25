package com.prettyshopbe.prettyshopbe.service;


import com.prettyshopbe.prettyshopbe.dto.product.ProductDto;
import com.prettyshopbe.prettyshopbe.model.Category;
import com.prettyshopbe.prettyshopbe.model.Product;
import com.prettyshopbe.prettyshopbe.respository.ProducRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.prettyshopbe.prettyshopbe.exceptions.ProductNotExistException;

@Service
public class ProductService {
    @Autowired
    ProducRespository producRespository;

    public List<ProductDto> listProducts() {
        List<Product> products = producRespository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = getDtoFromProduct(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }
    public Page<ProductDto> listProducts(Pageable pageable) {
        return producRespository.findAll(pageable).map(product -> new ProductDto(product));
    }


    public static ProductDto getDtoFromProduct(Product product) {
        ProductDto productDto = new ProductDto(product);
        return productDto;
    }

    public static Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product(productDto, category);
        return product;
    }

    public void addProduct(ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        producRespository.save(product);
    }

    public void updateProduct(Integer productID, ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        product.setId(productID);
        producRespository.save(product);
    }

    public Product getProductById(Integer productId) throws ProductNotExistException {
        Optional<Product> optionalProduct = producRespository.findById(productId);
        if (optionalProduct.isEmpty())
            throw new ProductNotExistException("Product id is invalid " + productId);
        return optionalProduct.get();
    }
}
