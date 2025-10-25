package com.example.shop.exceptions;

public class NotFoundProductException extends RuntimeException {
    public NotFoundProductException() {
        super("Product is not found");
    }
}
