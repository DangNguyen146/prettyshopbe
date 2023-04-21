 package com.prettyshopbe.prettyshopbe.respository;

 import com.prettyshopbe.prettyshopbe.model.Category;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

 @Repository
 public interface CategoryRepo extends JpaRepository<Category, Integer> {
     Category findByCategoryName(String categoryName);
 }
