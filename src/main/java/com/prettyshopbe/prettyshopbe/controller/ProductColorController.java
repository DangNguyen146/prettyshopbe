package com.prettyshopbe.prettyshopbe.controller;

import com.prettyshopbe.prettyshopbe.common.ApiResponse;
import com.prettyshopbe.prettyshopbe.model.ProductColor;
import com.prettyshopbe.prettyshopbe.respository.ProductColorRespository;
import com.prettyshopbe.prettyshopbe.service.ProductColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produccolor")
public class ProductColorController {
    @Autowired
    private ProductColorService productColorService;

    @Autowired
    ProductColorRespository productColorRespository;


    @GetMapping("/")
     public ResponseEntity<List<ProductColor>> getProductColor() {
        List<ProductColor> body = productColorService.listProductColor();
         return new ResponseEntity<List<ProductColor>>(body, HttpStatus.OK);
     }

     @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProducColor(@Valid @RequestBody ProductColor productColor) {
        productColorService.createProductColor(productColor);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Created the productColor"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> removeProductColor(@PathVariable Integer id) {
        productColorService.removeProductColor(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Removed the product color"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProductColor(@PathVariable Integer id, @Valid @RequestBody ProductColor productColor) {
        productColorService.updateProductColor(id, productColor);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Updated the product color"), HttpStatus.OK);
    }
}
