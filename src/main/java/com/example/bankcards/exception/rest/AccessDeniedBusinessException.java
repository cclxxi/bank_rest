package com.example.bankcards.exception.rest;

public class AccessDeniedBusinessException extends BusinessException {
    public AccessDeniedBusinessException(String message) {
        super(message);
    }
}
