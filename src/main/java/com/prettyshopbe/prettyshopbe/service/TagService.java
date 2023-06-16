package com.prettyshopbe.prettyshopbe.service;

import com.prettyshopbe.prettyshopbe.dto.TagDto;
import com.prettyshopbe.prettyshopbe.model.Product;
import com.prettyshopbe.prettyshopbe.model.ProductTag;
import com.prettyshopbe.prettyshopbe.model.Tag;
import com.prettyshopbe.prettyshopbe.respository.TagRespoitory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    public List<Tag> getProductTagsByProductId(Integer productId) {
        return tagRespoitory.findAllByProduct_Id(productId);
    }


    public Tag addProductToTag(Integer tagId, Integer productId) {
        ProductTag productTag = productTagService.findById(tagId).get();
        Product product = productService.getProductById(productId);


        Tag tag = new Tag(productTag, product);
        return tagRespoitory.save(tag);

    }

    public Tag updateProductInTag(Integer tagId, Integer productId) {
        Optional<Tag> optionalTag = tagRespoitory.findById(tagId);
        if (optionalTag.isPresent()) {
            Tag tag = optionalTag.get();
            Product product = productService.getProductById(productId);
            tag.setProduct(product);
            return tagRespoitory.save(tag);
        } else {
            return null;
        }
    }

    public boolean deleteTag(Integer tagId) {
        Optional<Tag> optionalTag = tagRespoitory.findById(tagId);
        if (optionalTag.isPresent()) {
            tagRespoitory.deleteById(tagId);
            return true;
        } else {
            return false;
        }
    }


    public Tag getRandomTag() {
        List<Tag> tagList = tagRespoitory.findAll();
        if (tagList.isEmpty()) {
            return null;
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(tagList.size());
            return tagList.get(randomIndex);
        }
    }



}
