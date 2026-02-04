package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Информация о роли пользователя")
public record RoleDTO(
        @Schema(description = "Название роли", example = "USER")
        String name
) {}
