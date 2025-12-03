package com.example.shop.controller;

import com.example.shop.dtos.ErrorDto;
import com.example.shop.dtos.ProductDto;
import com.example.shop.dtos.RegisterProdectRequest;
import com.example.shop.exceptions.NotAdminException;
import com.example.shop.exceptions.NotFoundProductException;
import com.example.shop.mappers.ProductMapper;
import com.example.shop.repositories.CategoryRepository;
import com.example.shop.repositories.ProductRepository;

import com.example.shop.services.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

private final ProductMapper productMapper;
private final ProductRepository productRepository;
private final CategoryRepository categoryRepository;
private final ProductsService  productsService;

@GetMapping
public List<ProductDto> findAllProducts(){
    return productsService.findAllProducts();
}
    @GetMapping("/{id}")
    public List<ProductDto> findProductsByCategory(@PathVariable("id") Byte category_id){
        return  productsService.findProductsByCategory(category_id);
    }

@PostMapping
public ResponseEntity<ProductDto> CreateProduct(@RequestBody RegisterProdectRequest  request){
    var productDto = productsService.CreateProduct(request);
    return  ResponseEntity.ok(productDto);

}
@PutMapping("/{id}")

public ResponseEntity<ProductDto> UpdateProduct(@RequestBody RegisterProdectRequest request, @PathVariable Long id){
    var productDto = productsService.UpdateProduct(request,id);
return ResponseEntity.ok(productDto);
}

}
