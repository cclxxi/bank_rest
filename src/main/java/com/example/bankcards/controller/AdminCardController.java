package com.example.bankcards.controller;

import com.example.bankcards.dto.AdminCardSummaryDTO;
import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.dto.CreateCardRequest;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/cards")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCardController {

    private final CardService cardService;

    @GetMapping
    public Page<AdminCardSummaryDTO> getAllCards(Pageable pageable) {
        return cardService.getAllCards(pageable);
    }

    @PostMapping
    public AdminCardSummaryDTO createCard(@RequestBody CreateCardRequest request) {
        return cardService.createCard(request);
    }

    @PatchMapping("/{cardId}/block")
    public void block(@PathVariable Long cardId) {
        cardService.blockCard(cardId);
    }

    @PatchMapping("/{cardId}/activate")
    public void activate(@PathVariable Long cardId) {
        cardService.activateCard(cardId);
    }

    @DeleteMapping("/{cardId}")
    public void deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
    }
}
