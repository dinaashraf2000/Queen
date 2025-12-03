package com.example.shop.services;

import com.example.shop.dtos.AddItemToCartRequest;
import com.example.shop.dtos.CartDto;
import com.example.shop.dtos.CartItemDto;
import com.example.shop.dtos.UpdateItemRequest;
import com.example.shop.entities.Cart;
import com.example.shop.entities.Product;
import com.example.shop.exceptions.NotFoundCartException;
import com.example.shop.exceptions.NotFoundProductException;
import com.example.shop.mappers.CartMapper;
import com.example.shop.repositories.CartRepository;
import com.example.shop.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    final private CartRepository cartRepository;
    final private CartMapper cartMapper;
    final private ProductRepository productRepository;

    public CartDto creatCart() {
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemDto addtoCart(UUID cartId, Long productId) {
        var cart = checkCart(cartId);

        var product = checkProduct(productId);

        var cartItem = cart.addItem(product);

        cartRepository.save(cart);
        return cartMapper.toCartItemDto(cartItem);

    }

    public CartDto getCart(UUID cartId) {
        var cart = checkCart(cartId);

        return cartMapper.toDto(cart);
    }

    public CartItemDto updateCart(UUID cartId, Long productId, int quantity) {

        var cart = checkCart(cartId);

        var product = checkProduct(productId);

        var cartItem = cart.getItem(productId);
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);

        return cartMapper.toCartItemDto(cartItem);
    }

    public void deleteCartItem(UUID cartId, Long productId) {

        var cart = checkCart(cartId);

        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {

        var cart = checkCart(cartId);

        cart.deleteCart();

        cartRepository.save(cart);
    }

    public Cart checkCart(UUID cartId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new NotFoundCartException();
        }
        return cart;
    }
public Product checkProduct(Long productId) {
    var product = productRepository.findById(productId).orElse(null);
    if (product == null) {
        throw new NotFoundProductException();
    }
    return product;
}
}