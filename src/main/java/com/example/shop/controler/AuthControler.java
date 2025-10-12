package com.example.shop.controler;

import com.example.shop.dtos.JwtResponce;
import com.example.shop.dtos.LoginDto;
import com.example.shop.dtos.UserDto;
import com.example.shop.services.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthControler {
private final AuthenticationManager authenticationManager;
private final JwtService jwtService;
    @PostMapping("/login")
    public ResponseEntity<JwtResponce> login(@Valid @RequestBody LoginDto loginDto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (loginDto.getEmail(), loginDto.getPassword()));

           var token = jwtService.generateToken(loginDto.getEmail());

            return ResponseEntity.ok(new JwtResponce(token));

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void>handlebadrequest() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
        }


