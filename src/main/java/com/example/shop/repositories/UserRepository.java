package com.example.shop.repositories;

import com.example.shop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    boolean existsByPassword(String password);

    Optional<User>findByEmail(String email);
}
