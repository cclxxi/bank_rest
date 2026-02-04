package com.example.bankcards.controller;

import com.example.bankcards.dto.AdminCardSummaryDTO;
import com.example.bankcards.dto.CreateCardRequest;
import com.example.bankcards.service.CardService;
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
@RequestMapping("/api/admin/cards")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Cards", description = "Управление картами (доступно только администратору)")
@SecurityRequirement(name = "bearerAuth")
public class AdminCardController {

    private final CardService cardService;

    @Operation(summary = "Получить все карты", description = "Возвращает список всех карт в системе с пагинацией")
    @GetMapping
    public Page<AdminCardSummaryDTO> getAllCards(@Parameter(description = "Параметры пагинации") Pageable pageable) {
        return cardService.getAllCards(pageable);
    }

    @Operation(summary = "Создать новую карту", description = "Создает новую карту для указанного аккаунта")
    @ApiResponse(responseCode = "201", description = "Карта успешно создана")
    @PostMapping
    public AdminCardSummaryDTO createCard(@RequestBody CreateCardRequest request) {
        return cardService.createCard(request);
    }

    @Operation(summary = "Заблокировать любую карту", description = "Административная блокировка карты")
    @PatchMapping("/{cardId}/block")
    public void block(@PathVariable Long cardId) {
        cardService.blockCard(cardId);
    }

    @Operation(summary = "Активировать карту", description = "Активация заблокированной карты")
    @PatchMapping("/{cardId}/activate")
    public void activate(@PathVariable Long cardId) {
        cardService.activateCard(cardId);
    }

    @Operation(summary = "Удалить карту", description = "Полное удаление карты из системы")
    @DeleteMapping("/{cardId}")
    public void deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
    }
}
