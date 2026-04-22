package com.rashed.ecommerce.orderservice.common.exception;

public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
}
