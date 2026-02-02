package com.example.bankcards.exception.rest;

public class ConflictException extends BusinessException {
    public ConflictException(String message) {
        super(message);
    }
}
