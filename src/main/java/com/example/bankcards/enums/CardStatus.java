package com.example.bankcards.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус банковской карты")
public enum CardStatus {
    @Schema(description = "Активна")
    ACTIVE,
    @Schema(description = "Заблокирована")
    BLOCKED,
    @Schema(description = "Истек срок действия")
    EXPIRED
}
