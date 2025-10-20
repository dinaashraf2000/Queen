package com.example.shop.mappers;
import com.example.shop.dtos.OrderDto;
import com.example.shop.dtos.OrderItemDto;
import com.example.shop.entities.Order;
import com.example.shop.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "items", target =  "orderItems")
    OrderDto toDto(Order order);
    OrderItemDto toCartItemDto(OrderItem orderItem);

}
