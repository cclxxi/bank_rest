package com.example.bankcards.dto;

public record AuthResponseDTO(
        UserProfileDTO user,
        String token
) {}
