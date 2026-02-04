package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ при успешной аутентификации")
public record AuthResponseDTO(
        @Schema(description = "Информация о профиле пользователя")
        UserProfileDTO user,
        @Schema(description = "JWT токен доступа", example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token
) {}
