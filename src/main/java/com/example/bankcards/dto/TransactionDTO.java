package com.example.bankcards.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(
        Long id,
        String type,
        String status,
        BigDecimal amount,
        LocalDateTime createdAt
) {}
