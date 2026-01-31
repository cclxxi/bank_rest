package com.example.bankcards.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UserTransactionRequest(

        @NotBlank
        String toAccountNumber,

        @Positive
        BigDecimal amount
) {}
