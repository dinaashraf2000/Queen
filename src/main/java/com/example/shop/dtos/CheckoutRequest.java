package com.example.shop.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CheckoutRequest {
    @NotNull(message = "cartId is requerd")
    UUID cartId;
}
