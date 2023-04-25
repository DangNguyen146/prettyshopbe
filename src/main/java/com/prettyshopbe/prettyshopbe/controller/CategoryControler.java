 package com.prettyshopbe.prettyshopbe.controller;

 import com.prettyshopbe.prettyshopbe.common.ApiResponse;


 import com.prettyshopbe.prettyshopbe.model.Category;
 import com.prettyshopbe.prettyshopbe.service.CategoryService;
 import com.prettyshopbe.prettyshopbe.until.Helper;



 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.HttpStatus;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.http.ResponseEntity;


 import javax.validation.Valid;
 import java.util.List;
 import java.util.Optional;


 @RestController
 @RequestMapping("/category")
 public class CategoryControler {
     @Autowired
     private CategoryService categoryService;


     @GetMapping("/")
     public ResponseEntity<List<Category>> getCategories() {
         List<Category> body = categoryService.listCategories();
         return new ResponseEntity<List<Category>>(body, HttpStatus.OK);
     }

     @GetMapping("/{categoryID}")
     public ResponseEntity<Category> getCategory(@PathVariable("categoryID") Integer categoryID) {
         Optional<Category> category = categoryService.readCategory(categoryID);
         if (category.isPresent()) {
             return ResponseEntity.ok(category.get());
         } else {
             return ResponseEntity.notFound().build();
         }
     }

     @PostMapping("/create")
     public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category){
         if(Helper.notNull(categoryService.readCategory(category.getCategoryName()))){
             return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category already exists"), HttpStatus.CONFLICT);
         }
         categoryService.createCategory(category);
         return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Created the category"), HttpStatus.CREATED);
     }

     @PutMapping("/update/{categoryID}")
     public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryID") Integer categoryID ,@Valid @RequestBody Category category){
        // Check category exit
         if(Helper.notNull(categoryService.readCategory(categoryID))){
             categoryService.updateCategory(categoryID,category);
             return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Complete update category"), HttpStatus.OK);
         }
         return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Category dosen't update"), HttpStatus.NOT_FOUND);
     }
 }
