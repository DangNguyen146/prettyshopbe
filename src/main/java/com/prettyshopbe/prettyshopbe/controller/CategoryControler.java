 package com.prettyshopbe.prettyshopbe.controller;

 import com.prettyshopbe.prettyshopbe.model.Category;
 import com.prettyshopbe.prettyshopbe.service.CategoryService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.HttpStatus;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.http.ResponseEntity;

 import java.util.List;


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

     @PostMapping("/create")
     public String createCategory(@RequestBody Category category){
         System.out.println(category);
         categoryService.createCategory(category);

         return "success";
     }
 }
