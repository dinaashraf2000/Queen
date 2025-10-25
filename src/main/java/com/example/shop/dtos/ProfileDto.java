package com.example.shop.dtos;

import com.example.shop.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class ProfileDto {
    private UserDto user;
    private String bio;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private Long loyaltyPoints;

}
