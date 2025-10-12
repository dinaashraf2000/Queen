package com.example.shop.mappers;

import com.example.shop.dtos.ProductDto;
import com.example.shop.dtos.RegisterProdectRequest;
import com.example.shop.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category",source = "category.id")
    ProductDto toDto(Product product);
    @Mapping(target = "category", ignore = true)
    Product toEntity(RegisterProdectRequest request);
    @Mapping(target = "category", ignore = true)
    void updateproduct(RegisterProdectRequest request,@MappingTarget Product product);
}
