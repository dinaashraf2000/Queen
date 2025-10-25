package com.example.shop.controller;

import com.example.shop.config.JwtConfig;
import com.example.shop.dtos.JwtResponce;
import com.example.shop.dtos.LoginDto;
import com.example.shop.dtos.UserDto;
import com.example.shop.mappers.UserMapper;
import com.example.shop.repositories.UserRepository;
import com.example.shop.services.AuthService;
import com.example.shop.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class
AuthController {
private final AuthenticationManager authenticationManager;
private final JwtService jwtService;
private final JwtConfig jwtConfig;
private final UserRepository userRepository;
private final UserMapper userMapper;
private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<JwtResponce> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (loginDto.getEmail(), loginDto.getPassword()));
var user=userRepository.findByEmail(loginDto.getEmail()).orElseThrow();
           var accessToken = jwtService.generateAccessToken(user);
var refreshToken = jwtService.generateFreshToken(user);
var cookie=new Cookie("refreshToken", refreshToken);
cookie.setPath("/auth/refresh");
cookie.setHttpOnly(true);
cookie.setSecure(true);
cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
response.addCookie(cookie);
            return ResponseEntity.ok(new JwtResponce(accessToken));

    }
    @PostMapping("/refresh")
public ResponseEntity<JwtResponce> refresh(@CookieValue(value = "refreshToken") String refreshToken
           ) {

if (!jwtService.validateToken(refreshToken)) {
    return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
}
var userId=jwtService.getUserIdFromToken(refreshToken);
var user=userRepository.findById(userId).orElse(null);
var accessToken = jwtService.generateAccessToken(user);
return ResponseEntity.ok(new JwtResponce(accessToken));
}

     @GetMapping("/me")
     public ResponseEntity<UserDto> me(){
         var user= authService.getCurrentUser();
        if(user==null)
            return ResponseEntity.notFound().build();

    var userDto=userMapper.toDto(user);
        return ResponseEntity.ok(userDto);

     }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void>handlebadrequest() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
        }


