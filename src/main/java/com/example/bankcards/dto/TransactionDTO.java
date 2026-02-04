package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Детальная информация о транзакции")
public record TransactionDTO(
        @Schema(description = "ID транзакции", example = "150")
        Long id,
        @Schema(description = "Тип транзакции (TRANSFER, DEPOSIT, WITHDRAW)", example = "TRANSFER")
        String type,
        @Schema(description = "Статус транзакции (PENDING, SUCCESS, FAILED)", example = "SUCCESS")
        String status,
        @Schema(description = "Сумма транзакции", example = "500.00")
        BigDecimal amount,
        @Schema(description = "Дата и время создания транзакции", example = "2023-10-01T14:30:00")
        LocalDateTime createdAt
) {}
