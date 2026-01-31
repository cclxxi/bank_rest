package com.example.bankcards.dto;

import java.time.LocalDateTime;

public record AdminUserSummaryDTO(
        Long id,
        String login,
        String role,
        boolean active,
        LocalDateTime createdAt
) {}
