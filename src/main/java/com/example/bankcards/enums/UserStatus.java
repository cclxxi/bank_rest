package com.example.bankcards.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус пользователя")
public enum UserStatus {
    @Schema(description = "Активен")
    ACTIVE,
    @Schema(description = "Заблокирован")
    BLOCKED
}
