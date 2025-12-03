package com.example.shop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // نحدد كل الميثودات داخل الـ services package
    @Pointcut("execution(* com.example.shop.services.*.*(..))")
    public void allServiceMethods() {}

    @Before("allServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("🟡 بدء تنفيذ: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "allServiceMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        System.out.println("🟢 انتهى: " + joinPoint.getSignature().getName() + " | النتيجة: " + result);
    }

    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        System.out.println("🔴 خطأ في: " + joinPoint.getSignature().getName() + " | السبب: " + ex.getMessage());
    }
}
