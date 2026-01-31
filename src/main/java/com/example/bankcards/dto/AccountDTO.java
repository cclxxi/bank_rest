package com.example.bankcards.dto;

import java.math.BigDecimal;

public record AccountDTO(
        String number,
        BigDecimal balance,
        String status
) {}
