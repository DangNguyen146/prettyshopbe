package com.prettyshopbe.prettyshopbe.controller;


import com.prettyshopbe.prettyshopbe.dto.ColorDto;
import com.prettyshopbe.prettyshopbe.dto.product.ProductDto;
import com.prettyshopbe.prettyshopbe.model.Color;
import com.prettyshopbe.prettyshopbe.respository.ColorRespository;
import com.prettyshopbe.prettyshopbe.service.ColorService;
import com.prettyshopbe.prettyshopbe.service.ProductColorService;
import com.prettyshopbe.prettyshopbe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/color")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @Autowired
    private ColorRespository colorRespoitory;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductColorService productColorService;

    @GetMapping("/{colorProductId}")
    public ResponseEntity<List<ProductDto>> getColor(@PathVariable("colorProductId") Integer colorProductId) {
        List<Color> body = colorService.readColorList(colorProductId);
        List<ProductDto> products = new ArrayList<ProductDto>();
        for (Color color : body) {
            products.add(ProductService.getDtoFromProduct(color.getProduct()));
        }

        return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
    }

    @PostMapping("/{colorId}/add-product")
    public ResponseEntity<ColorDto> addProductToColor(@PathVariable("colorId") Integer colorId,
                                                      @RequestParam("productId") Integer productId) {
        Color color = colorService.addProductToColor(colorId, productId);
        if (color == null) {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        } else {
            ColorDto dto = new ColorDto(color.getId(), color.getProductcolor().getId(),
                    ProductService.getDtoFromProduct(color.getProduct()));
            return new ResponseEntity<ColorDto>(dto, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{colorId}")
    public ResponseEntity<ColorDto> updateProductInColor(@PathVariable("colorId") Integer colorId,
            @RequestParam("productId") Integer productId) {
        Color color = colorService.updateProductInColor(colorId, productId);
        if (color == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ColorDto dto = new ColorDto(color.getId(), color.getProductcolor().getId(),
                    ProductService.getDtoFromProduct(color.getProduct()));
            return new ResponseEntity<ColorDto>(dto, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{colorId}")
    public ResponseEntity<Void> deleteColor(@PathVariable("colorId") Integer colorId) {
        boolean isDeleted = colorService.deleteColor(colorId);
        if (isDeleted) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/random")
    public ResponseEntity<ColorDto> getRandomColor() {
        Color color = colorService.getRandomColor();
        if (color == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ColorDto dto = new ColorDto(color.getId(), color.getProductcolor().getId(), ProductService.getDtoFromProduct(color.getProduct()));
            return new ResponseEntity<ColorDto>(dto, HttpStatus.OK);
        }
    }
}
