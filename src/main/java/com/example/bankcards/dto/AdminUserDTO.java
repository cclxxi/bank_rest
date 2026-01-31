package com.example.bankcards.dto;

import java.time.LocalDateTime;

public record AdminUserDTO(
        Long id,
        String login,
        String name,
        String surname,
        String role,
        boolean active,
        LocalDateTime createdAt
) {}
