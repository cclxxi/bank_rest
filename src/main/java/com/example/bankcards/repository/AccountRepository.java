package com.example.bankcards.repository;

import com.example.bankcards.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNumber(String number);
    Optional<Account> findByUser_Id(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findForUpdateByNumber(String number);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findForUpdateByUser_Id(Long userId);
}
