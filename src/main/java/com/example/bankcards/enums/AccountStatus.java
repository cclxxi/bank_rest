package com.example.bankcards.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус банковского счета")
public enum AccountStatus {
    @Schema(description = "Активен")
    ACTIVE,
    @Schema(description = "Заблокирован")
    BLOCKED,
    @Schema(description = "Закрыт")
    CLOSED
}
