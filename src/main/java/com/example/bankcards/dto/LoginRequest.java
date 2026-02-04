package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на вход в систему")
public record LoginRequest(

        @Schema(description = "Логин (email) пользователя", example = "user@example.com")
        @NotBlank
        String login,

        @Schema(description = "Пароль пользователя", example = "secret123")
        @NotBlank
        String password
) {}
