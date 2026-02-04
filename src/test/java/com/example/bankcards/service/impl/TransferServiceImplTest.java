package com.example.bankcards.service.impl;

import com.example.bankcards.entity.Account;
import com.example.bankcards.entity.User;
import com.example.bankcards.enums.AccountStatus;
import com.example.bankcards.enums.TransactionType;
import com.example.bankcards.exception.rest.AccessDeniedBusinessException;
import com.example.bankcards.exception.rest.ConflictException;
import com.example.bankcards.exception.rest.NotFoundException;
import com.example.bankcards.repository.AccountRepository;
import com.example.bankcards.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

    @Mock
    private CurrentUserServiceImpl currentUserService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    private User user;
    private Account fromAccount;
    private Account toAccount;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        lenient().when(user.getId()).thenReturn(1L);

        fromAccount = spy(new Account(user, "111"));
        lenient().when(fromAccount.getId()).thenReturn(101L);
        
        toAccount = spy(new Account(user, "222"));
        lenient().when(toAccount.getId()).thenReturn(102L);
        
        fromAccount.deposit(new BigDecimal("1000"));
    }

    @Test
    void transferMoney_Success() {
        String toAccNum = "222";
        BigDecimal amount = new BigDecimal("100");
        String ref = "ref1";

        when(currentUserService.getCurrentUserId()).thenReturn(1L);
        when(transactionRepository.existsByReference(ref)).thenReturn(false);
        when(accountRepository.findForUpdateByUser_Id(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByNumber(toAccNum)).thenReturn(Optional.of(toAccount));

        transferService.transferMoney(toAccNum, amount, ref);

        verify(fromAccount).withdraw(amount);
        verify(toAccount).deposit(amount);
        verify(transactionRepository).save(any());
    }

    @Test
    void transferMoney_Fail_NotOwnAccount() {
        User otherUser = mock(User.class);
        lenient().when(otherUser.getId()).thenReturn(2L);
        Account otherAccount = spy(new Account(otherUser, "333"));
        lenient().when(otherAccount.getId()).thenReturn(103L);

        String toAccNum = "333";
        BigDecimal amount = new BigDecimal("100");
        String ref = "ref2";

        when(currentUserService.getCurrentUserId()).thenReturn(1L);
        when(transactionRepository.existsByReference(ref)).thenReturn(false);
        when(accountRepository.findForUpdateByUser_Id(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByNumber(toAccNum)).thenReturn(Optional.of(otherAccount));

        assertThrows(AccessDeniedBusinessException.class, () -> 
            transferService.transferMoney(toAccNum, amount, ref)
        );
    }

    @Test
    void transferMoney_Fail_InsufficientFunds() {
        String toAccNum = "222";
        BigDecimal amount = new BigDecimal("2000"); // more than 1000
        String ref = "ref3";

        when(currentUserService.getCurrentUserId()).thenReturn(1L);
        when(transactionRepository.existsByReference(ref)).thenReturn(false);
        when(accountRepository.findForUpdateByUser_Id(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByNumber(toAccNum)).thenReturn(Optional.of(toAccount));

        assertThrows(IllegalArgumentException.class, () -> 
            transferService.transferMoney(toAccNum, amount, ref)
        );
    }
}
