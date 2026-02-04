package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Полная информация о пользователе для администратора")
public record AdminUserDTO(
        @Schema(description = "ID пользователя", example = "5")
        Long id,
        @Schema(description = "Логин (email) пользователя", example = "user@example.com")
        String login,
        @Schema(description = "Имя пользователя", example = "Иван")
        String name,
        @Schema(description = "Фамилия пользователя", example = "Иванов")
        String surname,
        @Schema(description = "Роль пользователя (ADMIN, USER)", example = "USER")
        String role,
        @Schema(description = "Статус активности аккаунта", example = "true")
        boolean active,
        @Schema(description = "Дата и время создания аккаунта", example = "2023-10-01T12:00:00")
        LocalDateTime createdAt
) {}
