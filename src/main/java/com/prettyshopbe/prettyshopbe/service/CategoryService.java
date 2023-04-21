 package com.prettyshopbe.prettyshopbe.service;

 import com.prettyshopbe.prettyshopbe.model.Category;
 import com.prettyshopbe.prettyshopbe.respository.CategoryRepo;
 import jakarta.transaction.Transactional;
 import org.springframework.stereotype.Service;

 import java.util.List;

 @Service
 @Transactional
 public class CategoryService {
     private final CategoryRepo categoryRepo;

     public List<Category> listCategories() {
         return categoryRepo.findAll();
     }

     public CategoryService(CategoryRepo categoryRepo) {
         this.categoryRepo = categoryRepo;
     }

     public void createCategory(Category category){
         categoryRepo.save(category);
     }
 }
