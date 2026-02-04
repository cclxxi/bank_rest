package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Информация о банковском счете")
public record AccountDTO(
        @Schema(description = "Номер счета", example = "40817810123456789012")
        String number,
        @Schema(description = "Текущий баланс", example = "1500.50")
        BigDecimal balance,
        @Schema(description = "Статус счета (ACTIVE, CLOSED, BLOCKED)", example = "ACTIVE")
        String status
) {}
