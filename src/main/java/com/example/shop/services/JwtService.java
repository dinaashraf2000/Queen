package com.example.shop.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secret;
    public String generateToken(String email) {
        final Long tokenExpiration = 86400L;
        return Jwts.builder().setSubject(email).setIssuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()*1000*tokenExpiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
}
