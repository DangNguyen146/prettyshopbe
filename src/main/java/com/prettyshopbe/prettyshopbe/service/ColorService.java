package com.prettyshopbe.prettyshopbe.service;


import com.prettyshopbe.prettyshopbe.model.Color;
import com.prettyshopbe.prettyshopbe.model.Product;
import com.prettyshopbe.prettyshopbe.model.ProductColor;
import com.prettyshopbe.prettyshopbe.model.Tag;
import com.prettyshopbe.prettyshopbe.respository.ColorRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
 @Transactional
public class ColorService {
     @Autowired
     ColorRespository colorRespoitory;



    @Autowired
    private ProductService productService;

    @Autowired
    private ProductColorService productColorService;

    public List<Color> readColorList(Integer colorProductId) {
        return colorRespoitory.findAllByProductColor_IdOrderByCreatedDateDesc(colorProductId);
    }


    public Color addProductToColor(Integer colorId, Integer productId) {
        ProductColor productColor = productColorService.findById(colorId).get();
        Product product = productService.getProductById(productId);


        Color color = new Color(productColor, product);
        return colorRespoitory.save(color);

    }

    public Color updateProductInColor(Integer colorId, Integer productId) {
        Optional<Color> optionalColor = colorRespoitory.findById(colorId);
        if (optionalColor.isPresent()) {
            Color color = optionalColor.get();
            Product product = productService.getProductById(productId);
            color.setProduct(product);
            return colorRespoitory.save(color);
        } else {
            return null;
        }
    }

    public boolean deleteColor(Integer colorId) {
        Optional<Color> optionalColor = colorRespoitory.findById(colorId);
        if (optionalColor.isPresent()) {
            colorRespoitory.deleteById(colorId);
            return true;
        } else {
            return false;
        }
    }


    public Color getRandomColor() {
        List<Color> colorList = colorRespoitory.findAll();
        if (colorList.isEmpty()) {
            return null;
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(colorList.size());
            return colorList.get(randomIndex);
        }
    }


    public List<Color> getProductColorsByProductId(Integer productId) {
        return colorRespoitory.findAllByProduct_Id(productId);
    }
}
