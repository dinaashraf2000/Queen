package com.example.shop.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //دي عشان لو هانقارن بين 2 يوزر
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<Address> addresses = new HashSet<>();

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    @ManyToMany
    private Set<Product> products = new HashSet<>();

}