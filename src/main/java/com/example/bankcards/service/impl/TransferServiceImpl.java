package com.example.bankcards.service.impl;

import com.example.bankcards.entity.Account;
import com.example.bankcards.entity.Transaction;
import com.example.bankcards.enums.TransactionType;
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
    public void transferMoney(Long toAccountId,
                              BigDecimal amount,
                              String reference) {

        Long currentUserId = currentUserService.getCurrentUserId();

        // 1. idempotency
        if (transactionRepository.existsByReference(reference)) {
            throw new IllegalStateException("Transaction already exists");
        }

        // 2. fromAccount с LOCK
        Account fromAccount = accountRepository
                .findForUpdateByUser_Id(currentUserId)
                .orElseThrow(() -> new IllegalStateException("Account not found"));

        // 3. toAccount без lock
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new IllegalStateException("Target account not found"));

        // 4. domain ownership check (на всякий случай)
        if (!fromAccount.getUser().getId().equals(currentUserId)) {
            throw new SecurityException("Access denied");
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
