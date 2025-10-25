package com.example.shop.controller;

import com.example.shop.dtos.ErrorDto;
import com.example.shop.dtos.OrderDto;
import com.example.shop.exceptions.OrderNotFoundException;
import com.example.shop.mappers.OrderMapper;
import com.example.shop.repositories.OrderRepository;
import com.example.shop.services.AuthService;
import com.example.shop.services.OrderServies;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrdersController {
    private final OrderServies orderServies;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final OrderMapper orderMapper;
    @GetMapping
    public List<OrderDto> getAllOrders() {

       return orderServies.getAllOrders();
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {

        var order = orderServies.getOrderById(id);

        return ResponseEntity.ok(order);
    }

    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<Void> handleOrderNotFoundException() {

        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorDto> AccessDeniedException(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorDto(e.getMessage()));
    }
}
