package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/cards")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class UserCardController {

    private final CardService cardService;
    private final CurrentUserService currentUserService;

    @GetMapping
    public Page<CardDTO> getMyCards(
            @RequestParam(required = false) String search,
            Pageable pageable
    ) {
        Long currentUserId = currentUserService.getCurrentUserId();
        return cardService.getUserCards(currentUserId, search, pageable);
    }

    @PostMapping("/{cardId}/block")
    public void block(@PathVariable Long cardId) {
        cardService.blockCard(cardId);
    }
}
