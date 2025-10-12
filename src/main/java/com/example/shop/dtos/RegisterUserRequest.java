package com.example.shop.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class RegisterUserRequest {
    @Size(min = 3, max = 20)
    @NotBlank(message = "no user name")
    private String name;
    @NotBlank
    @Size(min = 3, max = 30)
    private String email;
    @NotBlank
    private String password;

}
