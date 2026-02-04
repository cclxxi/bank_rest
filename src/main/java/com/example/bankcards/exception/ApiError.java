package com.example.bankcards.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

@Schema(description = "Информация об ошибке API")
public record ApiError(
        @Schema(description = "Сообщение об ошибке", example = "Ресурс не найден")
        String message,
        @Schema(description = "Временная метка ошибки", example = "2023-10-01T14:30:00Z")
        Instant timestamp
) {
    public static ApiError of(String message) {
        return new ApiError(message, Instant.now());
    }
}

