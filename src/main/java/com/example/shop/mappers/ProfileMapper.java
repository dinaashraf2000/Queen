package com.example.shop.mappers;

import com.example.shop.dtos.ProfileDto;
import com.example.shop.dtos.ProfileRequste;
import com.example.shop.entities.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileDto toDto(Profile profile);
    Profile toEntity(ProfileRequste request);
}
