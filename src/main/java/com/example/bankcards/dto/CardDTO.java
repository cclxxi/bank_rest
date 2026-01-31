package com.example.bankcards.dto;

import java.time.YearMonth;

public record CardDTO(
        Long id,
        String panMasked,
        String status,
        YearMonth expirationDate
) {}
