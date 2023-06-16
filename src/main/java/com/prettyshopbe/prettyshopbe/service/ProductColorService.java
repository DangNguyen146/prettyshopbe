package com.prettyshopbe.prettyshopbe.service;

import com.prettyshopbe.prettyshopbe.model.ProductColor;
import com.prettyshopbe.prettyshopbe.respository.ProductColorRespository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
 @Transactional
public class ProductColorService {
    private final ProductColorRespository productColorRespository;

    public ProductColorService(ProductColorRespository productColorRespository) {
        this.productColorRespository = productColorRespository;
    }

    public List<ProductColor> listProductColor() {
        return productColorRespository.findAll();
    }

    public void createProductColor(ProductColor productColor) {
        productColorRespository.save(productColor);
    }

    public void removeProductColor(Integer id) {
        productColorRespository.deleteById(id);
    }

    public void updateProductColor(Integer id, ProductColor productColor) {
        ProductColor currentProductColor = productColorRespository.findById(id).get();

        currentProductColor.setDescription(productColor.getDescription());
        currentProductColor.setColor(productColor.getColor());
        productColorRespository.save(currentProductColor);
    }

    public Optional<ProductColor> findById(Integer colorId) {
        return productColorRespository.findById(colorId);
    }
}
