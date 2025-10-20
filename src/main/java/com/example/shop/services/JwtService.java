package com.example.shop.services;

import com.example.shop.config.JwtConfig;
import com.example.shop.entities.Role;
import com.example.shop.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;

    public String generateAccessToken(User user) {

        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public String generateFreshToken(User user) {

        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private String generateToken(User user, long tokenExpiration) {
        String compact = Jwts.builder().setSubject(user.getId().toString())
                .claim("userId", user.getId())
                .claim("userEmail", user.getEmail())
                .claim("role",user.getRole())
                .setIssuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()  + tokenExpiration * 1000))
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                .compact();
        return compact;
    }

    public boolean validateToken(String token){
        try {
            return getClaims(token).getExpiration().after(new Date());
        }catch (JwtException e){
            return false;
        }
    }
    public Long getUserIdFromToken(String token){
        return Long.valueOf(getClaims(token).getSubject());
    }
    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
public Role getRoleFromToken(String token){
    return Role.valueOf(getClaims(token).get("role", String.class));
}
}
