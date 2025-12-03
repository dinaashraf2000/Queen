package com.example.shop.exceptions;

public class UserNotFoundException extends  RuntimeException {

    public UserNotFoundException(String userNotFound) {
        super(userNotFound);
    }
}
