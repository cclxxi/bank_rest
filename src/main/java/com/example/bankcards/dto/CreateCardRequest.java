package com.example.bankcards.dto;

import java.time.YearMonth;

public record CreateCardRequest(
        Long accountId,
        YearMonth expirationDate
) {
}
