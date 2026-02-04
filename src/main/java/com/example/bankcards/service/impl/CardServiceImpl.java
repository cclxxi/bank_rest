package com.example.bankcards.service.impl;

import com.example.bankcards.dto.AdminCardSummaryDTO;
import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.dto.CreateCardRequest;
import com.example.bankcards.entity.Account;
import com.example.bankcards.entity.Card;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.AccountRepository;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.CurrentUserService;
import com.example.bankcards.util.PanEncryptor;
import com.example.bankcards.util.PanGenerator;
import com.example.bankcards.util.PanMasker;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional()
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PanGenerator panGenerator;
    private final PanMasker panMasker;
    private final PanEncryptor panEncryptor;


    @Override
    public List<CardDTO> getUserCards(Long userId) {
        return cardRepository.findByAccount_User_Id(userId)
                .stream()
                .map(cardMapper::toDto)
                .toList();
    }

    @Override
    public Page<AdminCardSummaryDTO> getAllCards(Pageable pageable) {
        return cardRepository.findAll(pageable)
                .map(cardMapper::toAdminSummaryDto);
    }

    @Override
    public Page<CardDTO> getUserCards(Long userId, String search, Pageable pageable) {
        return cardRepository.findByAccount_User_IdWithSearch(userId, search, pageable)
                .map(cardMapper::toDto);
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

    @Override
    public void activateCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        card.activate();
    }

    @Override
    @Transactional
    public AdminCardSummaryDTO createCard(CreateCardRequest request) {

        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        // 1️⃣ expiration
        YearMonth expirationDate = YearMonth.now().plusYears(5);

        // 2️⃣ PAN
        String pan = panGenerator.generate();          // 16 digits
        String panMasked = panMasker.mask(pan);        // **** **** **** 1234
        String panEncrypted = panEncryptor.encrypt(pan);

        // 3️⃣ Card
        Card card = new Card(
                account,
                panEncrypted,
                panMasked,
                expirationDate
        );

        // 4️⃣ save
        Card saved = cardRepository.save(card);

        return cardMapper.toAdminSummaryDto(saved);
    }


    @Override
    public void deleteCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        cardRepository.delete(card);
    }

}
