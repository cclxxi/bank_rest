package com.example.bankcards.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус транзакции")
public enum TransactionStatus {
    @Schema(description = "Создана")
    CREATED,
    @Schema(description = "В обработке")
    PROCESSING,
    @Schema(description = "Успешно завершена")
    SUCCESS,
    @Schema(description = "Ошибка при выполнении")
    FAILED,
    @Schema(description = "Отменена/Возращена")
    REVERSED
}
