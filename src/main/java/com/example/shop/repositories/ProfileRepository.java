package com.example.shop.repositories;

import com.example.shop.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository  extends JpaRepository<Profile,Long> {
}
