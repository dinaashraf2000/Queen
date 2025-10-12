package com.example.shop.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateItemRequest {
    @NotNull(message = "Quantity must be provided")
    @Min(value = 1,message = "Quantity must be more than 1")
    @Max(value = 100 ,message = "Quantity must be less than or equal 100")
    private int quantity;
}
