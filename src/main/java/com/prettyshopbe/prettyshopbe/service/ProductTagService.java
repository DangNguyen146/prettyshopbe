package com.prettyshopbe.prettyshopbe.service;

import com.prettyshopbe.prettyshopbe.model.Category;
import com.prettyshopbe.prettyshopbe.model.ProductTag;
import com.prettyshopbe.prettyshopbe.respository.ProductTagRespository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
 @Transactional
public class ProductTagService {
    private final ProductTagRespository productTagRespository;

    public ProductTagService(ProductTagRespository productTagRespository) {
        this.productTagRespository = productTagRespository;
    }

    public List<ProductTag> listProductag() {
        return productTagRespository.findAll();
    }

//     public ProductTag readProductTag(String tag){
//        return productTagRespository.findByProductTag(tag);
//     }

    public void createProductTag(ProductTag productTag) {
        productTagRespository.save(productTag);
    }
}
