package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.CurrentUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/cards")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@Tag(name = "User Cards", description = "Управление картами текущего пользователя")
@SecurityRequirement(name = "bearerAuth")
public class UserCardController {

    private final CardService cardService;
    private final CurrentUserService currentUserService;

    @Operation(summary = "Получить список моих карт", description = "Возвращает список карт текущего пользователя с поддержкой пагинации и поиска по маске PAN")
    @GetMapping
    public Page<CardDTO> getMyCards(
            @Parameter(description = "Поиск по маскированному номеру карты (например, 1234)")
            @RequestParam(required = false) String search,
            @Parameter(description = "Параметры пагинации (page, size, sort)")
            Pageable pageable
    ) {
        Long currentUserId = currentUserService.getCurrentUserId();
        return cardService.getUserCards(currentUserId, search, pageable);
    }

    @Operation(summary = "Заблокировать карту", description = "Блокирует карту пользователя по её ID")
    @ApiResponse(responseCode = "200", description = "Карта успешно заблокирована")
    @ApiResponse(responseCode = "403", description = "Пользователь не является владельцем карты")
    @ApiResponse(responseCode = "404", description = "Карта не найдена")
    @PostMapping("/{cardId}/block")
    public void block(@PathVariable Long cardId) {
        cardService.blockCard(cardId);
    }
}
