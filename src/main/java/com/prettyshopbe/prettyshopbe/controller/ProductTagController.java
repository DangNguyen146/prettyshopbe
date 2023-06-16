package com.prettyshopbe.prettyshopbe.controller;

import com.prettyshopbe.prettyshopbe.common.ApiResponse;
import com.prettyshopbe.prettyshopbe.model.Category;
import com.prettyshopbe.prettyshopbe.model.ProductTag;
import com.prettyshopbe.prettyshopbe.respository.ProductTagRespository;
import com.prettyshopbe.prettyshopbe.service.ProductTagService;
import com.prettyshopbe.prettyshopbe.until.Helper;

import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prettyshopbe.prettyshopbe.service.CategoryService;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/productag")
public class ProductTagController {
    @Autowired
    private ProductTagService productTagService;

    @Autowired
    ProductTagRespository productTagRespository;


    @GetMapping("/")
     public ResponseEntity<List<ProductTag>> getProductTag() {
        List<ProductTag> body = productTagService.listProductag();
         return new ResponseEntity<List<ProductTag>>(body, HttpStatus.OK);
     }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProducTag(@Valid @RequestBody ProductTag productTag) {        
        productTagService.createProductTag(productTag);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Created the category"), HttpStatus.CREATED);
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> removeProductTag(@PathVariable Integer id) {
        productTagService.removeProductTag(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Removed the product tag"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProductTag(@PathVariable Integer id, @Valid @RequestBody ProductTag productTag) {
        productTagService.updateProductTag(id, productTag);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Updated the product tag"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductTag> getProductTagById(@PathVariable Integer id) {
        ProductTag productTag = productTagService.findById(id).get();
        if (productTag != null) {
            return new ResponseEntity<ProductTag>(productTag, HttpStatus.OK);
        } else {
            return new ResponseEntity<ProductTag>(HttpStatus.NOT_FOUND);
        }
    }


}
