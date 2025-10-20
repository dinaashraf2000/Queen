package com.example.shop.dtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProdectDto {
    public long id;
    public String name;
    public BigDecimal price;
}
