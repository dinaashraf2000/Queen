package com.example.shop.controler;

import com.example.shop.dtos.ProductDto;
import com.example.shop.dtos.RegisterProdectRequest;
import com.example.shop.dtos.UserDto;
import com.example.shop.entities.Product;
import com.example.shop.mappers.ProductMapper;
import com.example.shop.mappers.UserMapper;
import com.example.shop.repositories.CategoryRepository;
import com.example.shop.repositories.ProductRepository;
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
@GetMapping
public List<ProductDto> findAllProducts(@RequestParam(name = "categoryId", required = false) Byte category_id){
    List<Product> products;
    if(category_id != null){
        products = productRepository.findAllByCategory_Id(category_id);
    }
    else{
        products = productRepository.findAll();
    }
    return products
            .stream()
            .map(productMapper::toDto)
            .toList();
}
@PostMapping
public ResponseEntity<ProductDto> CreateProduct(@RequestBody RegisterProdectRequest  request){
    var category = categoryRepository.findById(request.getCategory());

    var product = productMapper.toEntity(request);
   // product.setCategory(category);
    productRepository.save(product);
    return  ResponseEntity.ok(productMapper.toDto(product));

}
@PutMapping("/{id}")
public ResponseEntity<ProductDto> UpdateProduct(@RequestBody RegisterProdectRequest request, @PathVariable Long id){
    var product = productRepository.findById(id).orElseThrow(null);

if(product == null){
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
    productMapper.updateproduct(request, product);
    productRepository.save(product);
return ResponseEntity.ok(productMapper.toDto(product));
}
}
