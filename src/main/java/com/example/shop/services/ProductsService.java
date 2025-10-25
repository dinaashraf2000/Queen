package com.example.shop.services;

import com.example.shop.dtos.ProductDto;
import com.example.shop.dtos.RegisterProdectRequest;
import com.example.shop.entities.Category;
import com.example.shop.entities.Product;
import com.example.shop.entities.Role;
import com.example.shop.exceptions.NotAdminException;
import com.example.shop.exceptions.NotFoundCategoryException;
import com.example.shop.exceptions.NotFoundProductException;
import com.example.shop.mappers.ProductMapper;
import com.example.shop.repositories.CategoryRepository;
import com.example.shop.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductsService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuthService authService;
    private final CategoryRepository categoryRepository;

    public List<ProductDto> findAllProducts(){
        List<Product> products;

        products = productRepository.findAll();

        return products
                .stream()
                .map(productMapper::toDto)
                .toList();
    }
    public List<ProductDto> findProductsByCategory(@RequestParam(name = "categoryId", required = false) Byte category_id){
        List<Product> products;

        if(category_id == null){
          throw new NotFoundProductException();
        }
        products = productRepository.findAllByCategory_Id(category_id);
        return products
                .stream()
                .map(productMapper::toDto)
                .toList();
    }
    public ProductDto CreateProduct(RegisterProdectRequest  request){
        var role = authService.getRole();

        if(role != Role.ADMIN){
            throw new NotAdminException();
        }

        Byte categoryId = request.getCategoryId();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundCategoryException::new);
        var product = productMapper.toEntity(request);
        product.setCategory(category);
        var savedProduct=productRepository.save(product);
        return  productMapper.toDto(savedProduct);

    }
    public ProductDto UpdateProduct(RegisterProdectRequest request, Long id){
        var product = productRepository.findById(id).orElse(null);

        if(product == null){
            throw new NotFoundProductException();
        }
        var role = authService.getRole();
        if(role != Role.ADMIN){
            throw new NotAdminException();
        }
        productMapper.updateproduct(request, product);
        var savedProduct= productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }
}
