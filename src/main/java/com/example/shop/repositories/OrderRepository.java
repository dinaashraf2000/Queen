package com.example.shop.repositories;

import com.example.shop.entities.Category;
import com.example.shop.entities.Order;
import com.example.shop.entities.OrderStatus;
import com.example.shop.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT o FROM Order o WHERE o.id = :orderId")
    Optional<Order> findByCustomerId(@Param("orderId") Long customerId);

    @EntityGraph(attributePaths = "items.product")
@Query("SELECT o FROM Order o WHERE o.customer = :customer")
    List<Order> findByCustomer(@Param("customer") User customer);
}
