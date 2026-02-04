package com.example.bankcards.service;

import com.example.bankcards.dto.AccountDTO;

import java.util.List;

public interface AccountService {

    AccountDTO getUserAccount();

    List<AccountDTO> getAllAccounts();

    void closeAccount(Long accountId);
}
