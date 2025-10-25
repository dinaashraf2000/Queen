package com.example.shop.controller;

import com.example.shop.dtos.ProfileDto;
import com.example.shop.dtos.ProfileRequste;
import com.example.shop.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private ProfileService profileService;
    @PostMapping
    public ResponseEntity<ProfileDto> createProfile(@RequestBody ProfileRequste request) {
        return ResponseEntity.ok(profileService.createProfile(request));
    }
    @GetMapping
    public ResponseEntity<ProfileDto> getProfile() {
        return ResponseEntity.ok(profileService.getProfile());
    }
}
