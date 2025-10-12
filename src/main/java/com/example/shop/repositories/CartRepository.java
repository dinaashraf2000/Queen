package com.example.shop.repositories;

import com.example.shop.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart,UUID> {
   //List<Cart>findById(UUID cartId);
}
