package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.YearMonth;

@Schema(description = "Информация о банковской карте для пользователя")
public record CardDTO(
        @Schema(description = "ID карты", example = "10")
        Long id,
        @Schema(description = "Маскированный номер карты", example = "**** **** **** 1234")
        String panMasked,
        @Schema(description = "Статус карты (ACTIVE, BLOCKED, EXPIRED)", example = "ACTIVE")
        String status,
        @Schema(description = "Срок действия карты", example = "2029-12")
        YearMonth expirationDate
) {}
