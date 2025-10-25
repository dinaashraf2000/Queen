package com.example.shop.exceptions;

public class NotFoundCategoryException extends RuntimeException {
    public NotFoundCategoryException() {
        super("Category is not found");

    }
}
