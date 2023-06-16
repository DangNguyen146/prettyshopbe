package com.prettyshopbe.prettyshopbe.respository;

import com.prettyshopbe.prettyshopbe.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  ColorRespository extends JpaRepository<Color, Integer> {

    List<Color> findAllByProductColor_IdOrderByCreatedDateDesc(Integer productColorId);
}
