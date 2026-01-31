package com.example.bankcards.repository;

import com.example.bankcards.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByReference(String reference);
    boolean existsByReference(String reference);

    List<Transaction> findByFromAccount_User_Id_OrderByCreatedAtDesc(Long fromAccountUserId);
    List<Transaction> findByToAccount_User_Id_OrderByCreatedAtDesc(Long toAccountUserId);
}
