package com.example.shop.dtos;

import com.example.shop.entities.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {


    private OrderProdectDto product;

    private Integer quantity;
    private BigDecimal unitPrice;

    private BigDecimal totalPrice;
}
