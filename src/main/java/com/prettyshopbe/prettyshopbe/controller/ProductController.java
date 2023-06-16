package com.prettyshopbe.prettyshopbe.controller;

import com.prettyshopbe.prettyshopbe.common.ApiResponse;


import com.prettyshopbe.prettyshopbe.dto.product.ProductDto;
import com.prettyshopbe.prettyshopbe.model.Category;
import com.prettyshopbe.prettyshopbe.model.Product;
import com.prettyshopbe.prettyshopbe.model.User;
import com.prettyshopbe.prettyshopbe.respository.CategoryRepo;
import com.prettyshopbe.prettyshopbe.service.AuthenticationService;
import com.prettyshopbe.prettyshopbe.service.CommentService;
import com.prettyshopbe.prettyshopbe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;




@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/")
    public ResponseEntity<Page<ProductDto>> getProducts(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductDto> products = productService.listProducts(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping("/{categoryid}")
    public ResponseEntity<Page<ProductDto>> getProductsByIdCategoryController(@PathVariable("categoryid") Integer categoryId,
                                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.getProductsByCategoryId(categoryId, pageable);
        Page<ProductDto> productDtos = products.map(ProductDto::new);
        return ResponseEntity.ok(productDtos);
    }
    @GetMapping("/getproduct/{id}")
    public ResponseEntity<ProductDto> getProductsByIdController(@PathVariable("id") Integer id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            ProductDto productDto = new ProductDto(product.get());
            return ResponseEntity.ok(productDto);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto){
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());

        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.addProduct(productDto,category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
    }

    @PostMapping("/update/{productID}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productID") Integer productID, @RequestBody @Valid ProductDto productDto) {
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.updateProduct(productID, productDto, category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchProducts(@RequestParam(value = "keyword") String keyword,
                                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.searchProducts(keyword, pageable);
        Page<ProductDto> productDtos = products.map(ProductDto::new);
        return ResponseEntity.ok(productDtos);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestParam("token") String token, @PathVariable("productId") Integer productId) {
        authenticationService.authenticate(token);
        // retrieve user
        User user = authenticationService.getUser(token);

        productService.deleteProduct(productId);
        return new ResponseEntity<>(new ApiResponse(true, "Product has been deleted"), HttpStatus.OK);
    }

    @PostMapping("/{productId}/addQuantityBySize")
    public ResponseEntity<ApiResponse> addQuantityBySize(@PathVariable("productId") Integer productId, @RequestBody @Valid QuantityBySizeRequest quantityBySizeRequest) {
        productService.addProductQuantityBySize(productId, quantityBySizeRequest.getSize(), quantityBySizeRequest.getQuantity());
        return new ResponseEntity<>(new ApiResponse(true, "Quantity By Size for Product has been updated"), HttpStatus.OK);
    }

    @PostMapping("/{productId}/updateQuantityBySize")
    public ResponseEntity<ApiResponse> updateQuantityBySize(@PathVariable("productId") Integer productId, @RequestBody @Valid QuantityBySizeRequest quantityBySizeRequest) {
        productService.updateProductQuantityBySize(productId, quantityBySizeRequest.getSize(), quantityBySizeRequest.getQuantity());
        return new ResponseEntity<>(new ApiResponse(true, "Quantity By Size for Product has been updated"), HttpStatus.OK);
    }

    @PostMapping("/{productId}/deleteQuantityBySize")
    public ResponseEntity<ApiResponse> deleteQuantityBySize(@PathVariable("productId") Integer productId, @RequestParam(value = "size") String size) {
        productService.deleteProductQuantityBySize(productId, size);
        return new ResponseEntity<>(new ApiResponse(true, "Quantity By Size for Product has been deleted"), HttpStatus.OK);
    }

    public static class QuantityBySizeRequest {
        private String size;
        private Integer quantity;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }


    @GetMapping("/accesscount/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Integer id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            // Increase access count of the product
            product.setAccessCount(product.getAccessCount() + 1);
            productService.updateProduct(product);

            ProductDto dto = ProductService.getDtoFromProduct(product);
            return new ResponseEntity<ProductDto>(dto, HttpStatus.OK);
        }
    }
    @Autowired
    CommentService commentService;

    @GetMapping("/{productId}/comments/count")
    public ResponseEntity<Long> getNumberOfComments(@PathVariable("productId") Integer productId) {
        Long count = commentService.getNumberOfCommentsByProductId(productId);
        return ResponseEntity.ok(count);
    }


}
