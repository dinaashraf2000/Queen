package com.example.shop.exceptions;

public class NotFoundCartException extends RuntimeException {
    public NotFoundCartException() {
        super("Cart is not found");
    }
}
