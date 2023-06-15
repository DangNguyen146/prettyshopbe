package com.prettyshopbe.prettyshopbe.service;

import com.prettyshopbe.prettyshopbe.model.Product;
import com.prettyshopbe.prettyshopbe.model.ProductTag;
import com.prettyshopbe.prettyshopbe.model.Tag;
import com.prettyshopbe.prettyshopbe.respository.TagRespoitory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TagService {
    @Autowired
    TagRespoitory tagRespoitory;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTagService productTagService;

    public List<Tag> readTagList(Integer tagProductId) {
        return tagRespoitory.findAllByProductTag_IdOrderByCreatedDateDesc(tagProductId);
    }


    public Tag addProductToTag(Integer tagId, Integer productId) {
        ProductTag productTag = productTagService.findById(tagId).get();
        Product product = productService.getProductById(productId);


        Tag tag = new Tag(productTag, product);
        return tagRespoitory.save(tag);

    }
}
