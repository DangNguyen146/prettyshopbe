 package com.prettyshopbe.prettyshopbe.service;

 import com.prettyshopbe.prettyshopbe.model.Category;
 import com.prettyshopbe.prettyshopbe.model.Product;
 import com.prettyshopbe.prettyshopbe.respository.CategoryRepo;
 import jakarta.transaction.Transactional;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;
 import org.springframework.stereotype.Service;

 import java.util.List;
 import java.util.Optional;

 @Service
 @Transactional
 public class CategoryService {
     private final CategoryRepo categoryRepo;

     public CategoryService(CategoryRepo categoryRepo) {
         this.categoryRepo = categoryRepo;
     }

     public List<Category> listCategories() {
         return categoryRepo.findAll();
     }


     public void createCategory(Category category){
         categoryRepo.save(category);
     }
     public Category readCategory(String categoryName){
        return categoryRepo.findByCategoryName(categoryName);
     }

     public Optional<Category> readCategory(Integer id){
         return  categoryRepo.findById(id);
     }

     public void updateCategory(Integer categoryID, Category newCategory){
         Category category= categoryRepo.findById(categoryID).get();
         category.setCategoryName(newCategory.getCategoryName());
         category.setDescription(newCategory.getDescription());
         category.setImageUrl(newCategory.getImageUrl());

         categoryRepo.save(category);
     }
     public Optional<Category> findById(Long id) {
         return categoryRepo.findById(id);
     }

     public Page<Category> searchCategoryByName(String categoryName, Pageable pageable) {
         return categoryRepo.findByCategoryNameContainingIgnoreCase(categoryName, pageable);
     }
 }
