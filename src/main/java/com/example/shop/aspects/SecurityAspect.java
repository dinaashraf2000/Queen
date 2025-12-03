package com.example.shop.aspects;

import com.example.shop.entities.Role;
import com.example.shop.exceptions.NotAdminException;
import com.example.shop.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class SecurityAspect {

    private final AuthService authService;

    @Before("@annotation(com.example.shop.annotations.AdminOnly)")
    public void checkAdminAccess() {
        var role = authService.getRole();
        if (role != Role.ADMIN) {
            throw new NotAdminException();
        }
    }
}
