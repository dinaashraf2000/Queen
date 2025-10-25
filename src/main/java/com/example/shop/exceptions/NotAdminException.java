package com.example.shop.exceptions;

public class NotAdminException extends RuntimeException {
    public NotAdminException() {

      super("Not Admin" );
    }
}
