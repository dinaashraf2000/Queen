package com.example.shop.mappers;

import com.example.shop.dtos.CartDto;
import com.example.shop.dtos.CartItemDto;
import com.example.shop.entities.Cart;
import com.example.shop.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(source = "items", target =  "cartItems")
    CartDto toDto(Cart cart);
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "price")
    CartItemDto toCartItemDto(CartItem cartItem);
    Cart toEntity(CartDto cartDto);

}
