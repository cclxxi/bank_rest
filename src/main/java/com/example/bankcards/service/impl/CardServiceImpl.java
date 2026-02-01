package com.example.bankcards.service.impl;

import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.entity.Card;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.CurrentUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional()
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final CurrentUserService currentUserService;


    @Override
    public List<CardDTO> getUserCards(Long userId) {
        return cardRepository.findByAccount_User_Id(userId)
                .stream()
                .map(cardMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void blockCard(Long cardId) {

        Long currentUserId = currentUserService.getCurrentUserId();

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));

        if (!card.getAccount().getUser().getId().equals(currentUserId)) {
            throw new AccessDeniedException("You do not own this card");
        }

        card.block();
    }

}
