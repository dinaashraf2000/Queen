package com.example.shop.controller;

import com.example.shop.dtos.CategoryDto;
import com.example.shop.dtos.CategoryRequest;
import com.example.shop.dtos.ErrorDto;
import com.example.shop.exceptions.NotAdminException;
import com.example.shop.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> findAll(){
return categoryService.findAllCategory();
    }
    @PostMapping
    public ResponseEntity<CategoryDto> registerCategory(@RequestBody CategoryRequest request){
var category=categoryService.createCategory(request);
  return ResponseEntity.ok(category);
    }
    @ExceptionHandler({NotAdminException.class})
    public ResponseEntity<ErrorDto> NotAdminException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto(e.getMessage()));
    }
}
