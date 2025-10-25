package com.example.shop.services;

import com.example.shop.dtos.*;
import com.example.shop.entities.Category;
import com.example.shop.entities.Product;
import com.example.shop.entities.Role;
import com.example.shop.exceptions.NotAdminException;
import com.example.shop.mappers.CategoryMapper;
import com.example.shop.mappers.ProductMapper;
import com.example.shop.repositories.CategoryRepository;
import com.example.shop.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AuthService authService;

    public List<CategoryDto> findAllCategory(){
        List<Category> category;

        category= categoryRepository.findAll();
       return category.stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public CategoryDto createCategory(CategoryRequest request) {

        var role = authService.getRole();
        if(role != Role.ADMIN){
            throw new NotAdminException();
        }

        var category = categoryMapper.toEntity(request);

        var savedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(savedCategory);
    }
}
