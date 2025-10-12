package com.example.shop.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDto {
    @NotBlank(message = "name is requerd")
    @Size(max = 10)
    private long id;
    private String name;
    private String email;
}
