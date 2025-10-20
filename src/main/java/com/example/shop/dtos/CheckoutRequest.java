package com.example.shop.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckoutDto {
    @NotNull(message = "cartId is requerd")
    UUID cartId;
}
