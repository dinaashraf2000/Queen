package com.example.shop.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class ProfileRequste {
    private String bio;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private Long loyaltyPoints;
}
