package com.prettyshopbe.prettyshopbe.service;

import com.prettyshopbe.prettyshopbe.dto.cart.AddToCartDto;
import com.prettyshopbe.prettyshopbe.dto.cart.CartDto;
import com.prettyshopbe.prettyshopbe.dto.cart.CartItemDto;
import com.prettyshopbe.prettyshopbe.exceptions.CartItemNotExistException;
import com.prettyshopbe.prettyshopbe.model.Cart;
import com.prettyshopbe.prettyshopbe.model.Product;
import com.prettyshopbe.prettyshopbe.model.User;
import com.prettyshopbe.prettyshopbe.respository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public CartService(){}

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart(AddToCartDto addToCartDto, Product product, User user, Map<String, Integer> quantityBySizes){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : quantityBySizes.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2); // remove the last ", "
        }
        String quantityBySizesString = sb.toString();

        Cart cart = new Cart(product, addToCartDto.getQuantity(), user, quantityBySizesString);
        cartRepository.save(cart);
    }


    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += (cartItemDto.getProduct().getPrice()* cartItemDto.getQuantity());
        }
        return new CartDto(cartItems,totalCost);
    }


    public static CartItemDto getDtoFromCart(Cart cart) {
        return new CartItemDto(cart);
    }


    public void updateCartItem(AddToCartDto cartDto, User user,Product product){
        Cart cart = cartRepository.getOne(cartDto.getId());
        cart.setQuantity(cartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);
    }

    public void deleteCartItem(int id,int userId) throws CartItemNotExistException {
        if (!cartRepository.existsById(id))
            throw new CartItemNotExistException("Cart id is invalid : " + id);
        cartRepository.deleteById(id);

    }

    public void deleteCartItems(int userId) {
        cartRepository.deleteAll();
    }


    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }
}
