package com.prettyshopbe.prettyshopbe.respository;

import com.prettyshopbe.prettyshopbe.model.Category;
import com.prettyshopbe.prettyshopbe.model.ProductTag;
import com.prettyshopbe.prettyshopbe.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTagRespository extends JpaRepository<ProductTag, Integer> {
    // ProductTag findByProductTagName(String tag);    
    Optional<ProductTag> findById(Integer id);

    //  List<ProductTag> findAll();

    // ProductTag findByProductTag(String tag);
}
