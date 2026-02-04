package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Запрос на перевод денежных средств")
public record UserTransactionRequest(

        @Schema(description = "Номер счета получателя", example = "40817810123456789012")
        @NotBlank
        String toAccountNumber,

        @Schema(description = "Сумма перевода", example = "500.00")
        @Positive
        BigDecimal amount
) {}
