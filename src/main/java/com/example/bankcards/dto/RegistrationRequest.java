package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Schema(description = "Запрос на регистрацию нового пользователя")
public record RegistrationRequest(

        @Schema(description = "Логин (email) для регистрации", example = "newuser@example.com")
        @NotBlank
        @Size(min = 3, max = 50)
        @Getter
        String login,

        @Schema(description = "Пароль (минимум 6 символов)", example = "password123")
        @NotBlank
        @Size(min = 6, max = 100)
        String password,

        @Schema(description = "Имя пользователя", example = "Иван")
        @NotBlank
        String name,

        @Schema(description = "Фамилия пользователя", example = "Иванов")
        @NotBlank
        String surname
) {}
