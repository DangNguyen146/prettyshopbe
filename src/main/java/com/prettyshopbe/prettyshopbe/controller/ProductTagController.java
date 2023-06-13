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
        System.out.println("da chay toi ay 0");
        List<ProductTag> body = productTagService.listProductag();
         System.out.println("da chay toi ay 1");
         return new ResponseEntity<List<ProductTag>>(body, HttpStatus.OK);
     }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProducTag(@Valid @RequestBody ProductTag productTag) {
        // if (Helper.notNull(productTagService.readProductTag(productTag.getTag()))) {
        //     return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category already exists"),
        //             HttpStatus.CONFLICT);
        // }
        productTagService.createProductTag(productTag);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Created the category"), HttpStatus.CREATED);
    }

}
