package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.YearMonth;

@Schema(description = "Запрос на создание новой карты")
public record CreateCardRequest(
        @Schema(description = "ID банковского счета, к которому привязывается карта", example = "1")
        Long accountId,
        @Schema(description = "Срок действия карты (необязательно, по умолчанию +5 лет)", example = "2030-12")
        YearMonth expirationDate
) {
}
