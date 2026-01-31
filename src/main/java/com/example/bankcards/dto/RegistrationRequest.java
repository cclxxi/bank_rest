package com.example.bankcards.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(

        @NotBlank
        @Size(min = 3, max = 50)
        String login,

        @NotBlank
        @Size(min = 6, max = 100)
        String password,

        @NotBlank
        String name,

        @NotBlank
        String surname
) {}
