 package com.prettyshopbe.prettyshopbe.respository;

 import com.prettyshopbe.prettyshopbe.model.Category;
 import com.prettyshopbe.prettyshopbe.model.Product;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

 import java.util.Optional;

 @Repository
 public interface CategoryRepo extends JpaRepository<Category, Integer> {
     Category findByCategoryName(String categoryName);

     Optional<Product> findById(Long id);
 }
