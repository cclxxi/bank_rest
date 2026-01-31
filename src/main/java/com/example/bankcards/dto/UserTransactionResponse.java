package com.example.bankcards.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UserTransactionResponse(
        Long transactionId,
        String status,
        BigDecimal amount,
        LocalDateTime createdAt
) {}
