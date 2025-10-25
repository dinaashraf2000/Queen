package com.example.shop.services;

import com.example.shop.dtos.ProfileDto;
import com.example.shop.dtos.ProfileRequste;
import com.example.shop.entities.Profile;
import com.example.shop.exceptions.UserNotFoundException;
import com.example.shop.mappers.ProfileMapper;
import com.example.shop.mappers.UserMapper;
import com.example.shop.repositories.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
private final AuthService authService;
private final UserMapper userMapper;

    public ProfileDto createProfile(ProfileRequste request) {
        var user = authService.getCurrentUser();

        if(user == null || user.getId() == null) {
            throw new UserNotFoundException("User not found or ID is missing");
        }
         var userDto= userMapper.toDto(user);

        var profile = profileMapper.toEntity(request);

         profile.setUsers(user);

        var savedProfile = profileRepository.save(profile);

        var profileDto = profileMapper.toDto(savedProfile);

        profileDto.setUser(userDto);

        return profileDto;
    }
    public ProfileDto getProfile() {
        var user = authService.getCurrentUser();
        if(user == null || user.getId() == null) {
            throw new UserNotFoundException("User not found or ID is missing");
        }
        Profile savedProfile = profileRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("Profile not found "));

        var profileDto = profileMapper.toDto(savedProfile);

        var userDto = userMapper.toDto(user);

        profileDto.setUser(userDto);

        return profileDto;
    }

}
