package com.example.shop.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CartDto {
    private UUID id;
    private List<CartItemDto> cartItems = new ArrayList<>();
    private BigDecimal totalPrice=BigDecimal.ZERO;

}
