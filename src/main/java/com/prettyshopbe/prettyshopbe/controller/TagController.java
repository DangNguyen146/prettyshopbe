package com.prettyshopbe.prettyshopbe.controller;

import com.prettyshopbe.prettyshopbe.dto.TagDto;
import com.prettyshopbe.prettyshopbe.dto.product.ProductDto;
import com.prettyshopbe.prettyshopbe.model.Product;
import com.prettyshopbe.prettyshopbe.model.ProductTag;
import com.prettyshopbe.prettyshopbe.model.Tag;
import com.prettyshopbe.prettyshopbe.respository.TagRespoitory;
import com.prettyshopbe.prettyshopbe.service.ProductService;
import com.prettyshopbe.prettyshopbe.service.ProductTagService;
import com.prettyshopbe.prettyshopbe.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @Autowired
    private TagRespoitory tagRespoitory;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTagService productTagService;

    @GetMapping("/{tagProductId}")
    public ResponseEntity<List<ProductDto>> getTag(@PathVariable("tagProductId") Integer tagProductId) {
        List<Tag> body = tagService.readTagList(tagProductId);
        List<ProductDto> products = new ArrayList<ProductDto>();
        for (Tag tag : body) {
            products.add(ProductService.getDtoFromProduct(tag.getProduct()));
        }

        return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
    }



}
