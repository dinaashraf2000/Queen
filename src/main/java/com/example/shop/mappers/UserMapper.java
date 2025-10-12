package com.example.shop.mappers;

import com.example.shop.dtos.RegisterUserRequest;
import com.example.shop.dtos.UpdateUserRequest;
import com.example.shop.dtos.UserDto;
import com.example.shop.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
UserDto toDto(User user);
User toEntity(RegisterUserRequest request);
void updateUser(UpdateUserRequest request, @MappingTarget User user);
}
