package com.example.shop.repositories;

import com.example.shop.dtos.ProductDto;
import com.example.shop.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
List<Product> findAllByCategory_Id(Byte category_id);
}
