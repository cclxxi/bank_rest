package com.example.bankcards.dto;

import java.time.YearMonth;

public record AdminCardSummaryDTO(
        Long cardId,
        String panMasked,
        String status,
        YearMonth expirationDate,
        String accountNumber,
        Long userId
) {}
