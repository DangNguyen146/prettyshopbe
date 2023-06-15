package com.prettyshopbe.prettyshopbe.service;

import com.prettyshopbe.prettyshopbe.model.Product;
import com.prettyshopbe.prettyshopbe.model.Tag;
import com.prettyshopbe.prettyshopbe.respository.TagRespoitory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TagService {
    @Autowired
    TagRespoitory tagRespoitory;

    public List<Tag> readTagList(Integer tagProductId) {
        return tagRespoitory.findAllByProductTag_IdOrderByCreatedDateDesc(tagProductId);
    }




}
