package com.example.bankcards.service.impl;

import com.example.bankcards.dto.AdminCardSummaryDTO;
import com.example.bankcards.dto.CreateCardRequest;
import com.example.bankcards.entity.Account;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.AccountRepository;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.service.CurrentUserService;
import com.example.bankcards.util.PanEncryptor;
import com.example.bankcards.util.PanGenerator;
import com.example.bankcards.util.PanMasker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;
    @Mock
    private CardMapper cardMapper;
    @Mock
    private CurrentUserService currentUserService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PanGenerator panGenerator;
    @Mock
    private PanMasker panMasker;
    @Mock
    private PanEncryptor panEncryptor;

    @InjectMocks
    private CardServiceImpl cardService;

    private User user;
    private Account account;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        account = mock(Account.class);
        lenient().when(account.getUser()).thenReturn(user);
    }

    @Test
    void createCard_Success() {
        CreateCardRequest request = new CreateCardRequest(1L, null);
        
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(panGenerator.generate()).thenReturn("1234123412341234");
        when(panMasker.mask(any())).thenReturn("**** **** **** 1234");
        when(panEncryptor.encrypt(any())).thenReturn("encrypted");
        
        when(cardRepository.save(any(Card.class))).thenAnswer(i -> i.getArguments()[0]);
        when(cardMapper.toAdminSummaryDto(any(Card.class))).thenReturn(mock(AdminCardSummaryDTO.class));

        AdminCardSummaryDTO result = cardService.createCard(request);

        assertNotNull(result);
        verify(cardRepository).save(any(Card.class));
    }

    @Test
    void blockCard_Success() {
        Long cardId = 10L;
        Card card = mock(Card.class);
        when(card.getAccount()).thenReturn(account);
        when(user.getId()).thenReturn(1L);
        when(currentUserService.getCurrentUserId()).thenReturn(1L);
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        cardService.blockCard(cardId);

        verify(card).block();
    }
}
