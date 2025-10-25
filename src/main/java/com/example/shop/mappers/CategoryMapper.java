package com.example.shop.mappers;

import com.example.shop.dtos.CategoryDto;
import com.example.shop.dtos.CategoryRequest;
import com.example.shop.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CategoryRequest request);

}
