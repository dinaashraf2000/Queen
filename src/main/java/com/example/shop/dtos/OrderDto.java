package com.example.shop.dtos;

import com.example.shop.entities.OrderItem;
import com.example.shop.entities.OrderStatus;
import com.example.shop.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private List<OrderItemDto> orderItems = new ArrayList<>();
    private BigDecimal totalPrice;

}
