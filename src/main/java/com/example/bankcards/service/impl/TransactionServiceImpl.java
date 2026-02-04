package com.example.bankcards.service.impl;

import com.example.bankcards.dto.TransactionDTO;
import com.example.bankcards.entity.Transaction;
import com.example.bankcards.mapper.TransactionMapper;
import com.example.bankcards.repository.TransactionRepository;
import com.example.bankcards.service.CurrentUserService;
import com.example.bankcards.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final CurrentUserService currentUserService;

    @Override
    public List<TransactionDTO> getUserTransactions() {

        Long currentUserId = currentUserService.getCurrentUserId();

        List<Transaction> outgoing =
                transactionRepository.findByFromAccount_User_IdOrderByCreatedAtDesc(currentUserId);

        List<Transaction> incoming =
                transactionRepository.findByToAccount_User_IdOrderByCreatedAtDesc(currentUserId);

        return Stream.concat(outgoing.stream(), incoming.stream())
                .sorted(Comparator.comparing(Transaction::getCreatedAt).reversed())
                .map(transactionMapper::toDto)
                .toList();
    }
}

