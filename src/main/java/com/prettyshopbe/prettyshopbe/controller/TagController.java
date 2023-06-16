package com.prettyshopbe.prettyshopbe.controller;

import com.prettyshopbe.prettyshopbe.dto.ProductTagDto;
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

    @PostMapping("/{tagId}/add-product")
    public ResponseEntity<TagDto> addProductToTag(@PathVariable("tagId") Integer tagId,
            @RequestParam("productId") Integer productId) {
        Tag tag = tagService.addProductToTag(tagId, productId);
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        } else {
            TagDto dto = new TagDto(tag.getId(), tag.getProducttag().getId(),
                    ProductService.getDtoFromProduct(tag.getProduct()));
            return new ResponseEntity<TagDto>(dto, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<TagDto> updateProductInTag(@PathVariable("tagId") Integer tagId,
            @RequestParam("productId") Integer productId) {
        Tag tag = tagService.updateProductInTag(tagId, productId);
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            TagDto dto = new TagDto(tag.getId(), tag.getProducttag().getId(),
                    ProductService.getDtoFromProduct(tag.getProduct()));
            return new ResponseEntity<TagDto>(dto, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable("tagId") Integer tagId) {
        boolean isDeleted = tagService.deleteTag(tagId);
        if (isDeleted) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/random")
    public ResponseEntity<TagDto> getRandomTag() {
        Tag tag = tagService.getRandomTag();
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            TagDto dto = new TagDto(tag.getId(), tag.getProducttag().getId(), ProductService.getDtoFromProduct(tag.getProduct()));
            return new ResponseEntity<TagDto>(dto, HttpStatus.OK);
        }
    }


//    @GetMapping("/product/{productId}/tags")
//    public ResponseEntity<List<Tag>> getProductTagsByProductId(@PathVariable("productId") Integer productId) {
//        List<Tag> productTags = tagService.getProductTagsByProductId(productId);
//
//
//            return new ResponseEntity<>(productTags, HttpStatus.OK);
//
//    }

    @GetMapping("/product/{productId}/tags")
    public ResponseEntity<List<ProductTagDto>> getProductTagsByProductId(@PathVariable("productId") Integer productId) {
        List<Tag> productTags = tagService.getProductTagsByProductId(productId);

        if (productTags.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<ProductTagDto> productTagDtos = new ArrayList<>();

            for (Tag productTag : productTags) {
                productTagDtos.add(new ProductTagDto(productTag.getId(), productTag.getProducttag().getTag(), productTag.getProducttag().getDescription()));
            }

            return new ResponseEntity<>(productTagDtos, HttpStatus.OK);
        }
    }
}
