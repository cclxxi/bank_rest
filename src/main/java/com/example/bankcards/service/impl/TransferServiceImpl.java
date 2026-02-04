package com.example.bankcards.service.impl;

import com.example.bankcards.entity.Account;
import com.example.bankcards.entity.Transaction;
import com.example.bankcards.enums.TransactionType;
import com.example.bankcards.exception.rest.AccessDeniedBusinessException;
import com.example.bankcards.exception.rest.ConflictException;
import com.example.bankcards.exception.rest.NotFoundException;
import com.example.bankcards.repository.AccountRepository;
import com.example.bankcards.repository.TransactionRepository;
import com.example.bankcards.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final CurrentUserServiceImpl currentUserService;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void transferMoney(String toAccountNumber,
                              BigDecimal amount,
                              String reference) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ConflictException("Amount must be positive");
        }

        Long currentUserId = currentUserService.getCurrentUserId();

        // 1. idempotency
        if (transactionRepository.existsByReference(reference)) {
            throw new ConflictException("Transaction already exists");
        }

        // 2. fromAccount с LOCK
        Account fromAccount = accountRepository
                .findForUpdateByUser_Id(currentUserId)
                .orElseThrow(() -> new ConflictException("Account not found"));

        // 3. toAccount без lock
        Account toAccount = accountRepository.findByNumber(toAccountNumber)
                .orElseThrow(() -> new NotFoundException("Target account not found"));

        // 4. domain ownership check (на всякий случай)
        if (!fromAccount.getUser().getId().equals(currentUserId)) {
            throw new AccessDeniedBusinessException("Access denied");
        }

        // 5. создать транзакцию
        Transaction transaction = new Transaction(
                fromAccount,
                toAccount,
                amount,
                reference,
                TransactionType.TRANSFER
        );

        transaction.markProcessing();

        // 6. деньги (ВАЖНЫЙ ПОРЯДОК)
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        // 7. завершить транзакцию
        transaction.markSuccess();

        // 8. сохранить факт операции
        transactionRepository.save(transaction);
    }
}
