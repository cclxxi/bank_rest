package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Информация о профиле пользователя")
public record UserProfileDTO(
        @Schema(description = "Логин (email) пользователя", example = "user@example.com")
        String login,
        @Schema(description = "Имя пользователя", example = "Иван")
        String name,
        @Schema(description = "Фамилия пользователя", example = "Иванов")
        String surname
) {}
