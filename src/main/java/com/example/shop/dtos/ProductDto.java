package com.example.shop.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductDto {
    public long id;
    public String name;
    public String description;
    public double price;
    public Byte category;
}
