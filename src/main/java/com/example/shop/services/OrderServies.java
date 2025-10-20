package com.example.shop.services;

import com.example.shop.dtos.OrderDto;
import com.example.shop.exceptions.OrderNotFoundException;
import com.example.shop.mappers.OrderMapper;
import com.example.shop.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServies {
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {

        var user= authService.getCurrentUser();
        var order = orderRepository.findByCustomer(user);

        return order.stream().map(orderMapper::toDto).toList();
    }
    public OrderDto getOrderById(Long id) {
        var order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);

        var user= authService.getCurrentUser();
        if(!user.equals(order.getCustomer())) {
            throw new AccessDeniedException("You are not allowed to access this resource");
        }
        return orderMapper.toDto(order);
    }

    }

