package com.example.shop.dtos;

import com.example.shop.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private List<ProductDto> products = new ArrayList<>();
}
