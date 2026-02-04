package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.YearMonth;

@Schema(description = "Краткая информация о карте для администратора")
public record AdminCardSummaryDTO(
        @Schema(description = "ID карты", example = "10")
        Long cardId,
        @Schema(description = "Маскированный номер карты", example = "**** **** **** 1234")
        String panMasked,
        @Schema(description = "Статус карты (ACTIVE, BLOCKED, EXPIRED)", example = "ACTIVE")
        String status,
        @Schema(description = "Срок действия карты", example = "2029-12")
        YearMonth expirationDate,
        @Schema(description = "Номер счета, к которому привязана карта", example = "40817810123456789012")
        String accountNumber,
        @Schema(description = "ID владельца карты", example = "5")
        Long userId
) {}
