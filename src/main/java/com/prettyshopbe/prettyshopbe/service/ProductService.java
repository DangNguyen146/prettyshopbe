package com.prettyshopbe.prettyshopbe.service;


import com.prettyshopbe.prettyshopbe.dto.product.ProductDto;
import com.prettyshopbe.prettyshopbe.model.Category;
import com.prettyshopbe.prettyshopbe.model.Product;
import com.prettyshopbe.prettyshopbe.respository.CategoryRepo;
import com.prettyshopbe.prettyshopbe.respository.ProducRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.prettyshopbe.prettyshopbe.exceptions.ProductNotExistException;

@Service
public class ProductService {
    @Autowired
    ProducRespository producRespository;

    @Autowired
    CategoryRepo categoryRepo;

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
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdDate").descending());
        return producRespository.findAll(pageRequest).map(product -> new ProductDto(product));
    }



    public static ProductDto getDtoFromProduct(Product product) {
        ProductDto productDto = new ProductDto(product);
        return productDto;
    }

    public static Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product(productDto, category);
        return product;
    }


    public Page<Product> getProductsByCategoryId(Integer categoryId, Pageable pageable) {
        return producRespository.findByCategoryIdOrderByCreatedDateDesc(categoryId, pageable);
    }

    public void addProduct(ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        product.setCreatedDate(new Date());
        producRespository.save(product);
    }

    public void updateProduct(Integer productID, ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        product.setId(productID);
        product.setCreatedDate(new Date());
        producRespository.save(product);
    }

    public Product getProductById(Integer productId) throws ProductNotExistException {
        Optional<Product> optionalProduct = producRespository.findById(productId);
        if (optionalProduct.isEmpty())
            throw new ProductNotExistException("Product id is invalid " + productId);
        return optionalProduct.get();
    }

    public Optional<Product> findById(Integer id) {
        return producRespository.findById(id);
    }

    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        return producRespository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    public void deleteProduct(Integer productID) {
        producRespository.deleteById(productID);
    }

    public void addProductQuantityBySize(Integer productId, String size, Integer quantity) {
        Optional<Product> optionalProduct = producRespository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setCreatedDate(new Date());
            List<String> sizeList = product.getSize();
            List<Integer> quantityBySizesList = product.getQuantityBySizes();
            int index = sizeList.indexOf(size);
            if (index != -1) {
                quantityBySizesList.set(index, quantityBySizesList.get(index) + quantity);
            } else {
                sizeList.add(size);
                quantityBySizesList.add(quantity);
            }
            producRespository.save(product);
        }
    }

    public void updateProductQuantityBySize(Integer productId, String size, Integer quantity) {
        Optional<Product> optionalProduct = producRespository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setCreatedDate(new Date());
            List<String> sizeList = product.getSize();
            List<Integer> quantityBySizes = product.getQuantityBySizes();
            int index = sizeList.indexOf(size);
            if (index != -1) {
                quantityBySizes.set(index, quantity);
            }
            producRespository.save(product);
        }
    }

    public void deleteProductQuantityBySize(Integer productId, String size) {
        Optional<Product> optionalProduct = producRespository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            List<String> sizeList = product.getSize();
            List<Integer> quantityBySizes = product.getQuantityBySizes();
            int index = sizeList.indexOf(size);
            if (index != -1) {
                sizeList.remove(index);
                quantityBySizes.remove(index);
            }
            producRespository.save(product);
        }
    }

    public Product updateProduct(Product product) {
        product.setCreatedDate(new Date());
        return producRespository.save(product);
    }
}
