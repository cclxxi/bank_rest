package com.example.bankcards.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Тип транзакции")
public enum TransactionType {
    @Schema(description = "Пополнение")
    DEPOSIT,
    @Schema(description = "Снятие")
    WITHDRAWAL,
    @Schema(description = "Перевод")
    TRANSFER
}
