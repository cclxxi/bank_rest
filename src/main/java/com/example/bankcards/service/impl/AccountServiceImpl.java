package com.example.bankcards.service.impl;

import com.example.bankcards.dto.AccountDTO;
import com.example.bankcards.entity.Account;
import com.example.bankcards.mapper.AccountMapper;
import com.example.bankcards.repository.AccountRepository;
import com.example.bankcards.service.AccountService;
import com.example.bankcards.service.CurrentUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CurrentUserService currentUserService;

    @Override
    public AccountDTO getUserAccount() {
        Account account = findCurrentUserAccount();
        return accountMapper.toDto(account);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void closeAccount(Long accountId) {
        Account account = findAccount(accountId);
        account.close();
    }

    private Account findAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    private Account findCurrentUserAccount() {
        return accountRepository.findByUser_Id(currentUserService.getCurrentUserId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found for current user"));
    }
}

