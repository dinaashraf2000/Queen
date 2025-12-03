package com.example.shop.aspects;

import com.example.shop.exceptions.UserNotFoundException;
import com.example.shop.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthenticationAspect {

    private final AuthService authService;

    @Before("@annotation(com.example.shop.annotations.Authenticated)")
    public void checkAuthentication() {
        var user = authService.getCurrentUser();
        if (user == null || user.getId() == null) {
            throw new UserNotFoundException("Not login");
        }
    }
}
