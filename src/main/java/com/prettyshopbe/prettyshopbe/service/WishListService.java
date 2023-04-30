package com.prettyshopbe.prettyshopbe.service;

import com.prettyshopbe.prettyshopbe.model.WishList;
import com.prettyshopbe.prettyshopbe.respository.WishListRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class WishListService {

    @Autowired
    WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void createWishlist(WishList wishList) {
        System.out.println(wishList.getProduct().getName());
        System.out.println(wishList.getProduct().getId());
        System.out.println(wishList.getUser().getId());
        wishListRepository.save(wishList);
    }

    public List<WishList> readWishList(Integer userId) {
        return wishListRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
    }
}
