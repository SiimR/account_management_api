package com.example.domain.exception;

public class AccountValidationException extends RuntimeException {
    public AccountValidationException(String message) {
        super(message);
    }
}
