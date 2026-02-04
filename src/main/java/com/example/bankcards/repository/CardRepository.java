package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.enums.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByAccount_User_Id(Long userId);

    @Query("SELECT c FROM Card c WHERE c.account.user.id = :userId AND (:search IS NULL OR c.panMasked LIKE %:search%)")
    Page<Card> findByAccount_User_IdWithSearch(Long userId, String search, Pageable pageable);

    List<Card> findByAccount_User_IdAndStatus(Long account_user_id, CardStatus status);

}
