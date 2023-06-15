package com.prettyshopbe.prettyshopbe.service;

import com.prettyshopbe.prettyshopbe.model.Category;
import com.prettyshopbe.prettyshopbe.model.ProductTag;
import com.prettyshopbe.prettyshopbe.respository.ProductTagRespository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void removeProductTag(Integer id) {
        productTagRespository.deleteById(id);
    }

    public void updateProductTag(Integer id, ProductTag productTag) {
        ProductTag currentProductTag = productTagRespository.findById(id).get();

        currentProductTag.setDescription(productTag.getDescription());
        currentProductTag.setTag(productTag.getTag());
        productTagRespository.save(currentProductTag);
    }

    public Optional<ProductTag> findById(Integer tagId) {
        return productTagRespository.findById(tagId);
    }
}
