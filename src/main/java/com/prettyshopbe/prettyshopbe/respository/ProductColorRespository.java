package com.prettyshopbe.prettyshopbe.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prettyshopbe.prettyshopbe.model.ProductColor;

import java.util.Optional;


@Repository
public interface ProductColorRespository extends JpaRepository<ProductColor, Integer> {
    Optional<ProductColor> findById(Integer id);
}
