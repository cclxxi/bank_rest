package com.example.bankcards.service;

import com.example.bankcards.dto.AdminCardSummaryDTO;
import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.dto.CreateCardRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CardService {

    List<CardDTO> getUserCards(Long userId);

    Page<CardDTO> getUserCards(Long userId, String search, Pageable pageable);

    Page<AdminCardSummaryDTO> getAllCards(Pageable pageable);

    void blockCard(Long cardId);

    void activateCard(Long cardId);

    AdminCardSummaryDTO createCard(CreateCardRequest request);

    void deleteCard(Long cardId);
}

