package com.prettyshopbe.prettyshopbe.respository;

import com.prettyshopbe.prettyshopbe.model.ProductTag;
import com.prettyshopbe.prettyshopbe.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface TagRespoitory extends JpaRepository<Tag, Integer> {
    List<Tag> findAllByProductTag_IdOrderByCreatedDateDesc(Integer productTagId);

    List<Tag> findAllByProduct_Id(Integer productId);

    List<Tag> findByNameContainsIgnoreCase(String name);
}
