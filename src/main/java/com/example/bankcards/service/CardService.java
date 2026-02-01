package com.example.bankcards.service;

import com.example.bankcards.dto.CardDTO;

import java.util.List;

public interface CardService {

    List<CardDTO> getUserCards(Long userId);

    void blockCard(Long cardId);
}

