package com.example.shop.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterProdectRequest {
   private String name;
    private String description;
    private double price;
    private Byte category;
}
