package com.example.bankcards.entity;

import com.example.bankcards.enums.CardStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Entity
@Getter
@Table(name = "cards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "panEncrypted")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_seq")
    @Column(name = "card_id")
    private Long id;

    @Column(name = "pan_encrypted", unique = true, nullable = false, updatable = false)
    private String panEncrypted;

    @Column(name = "pan_masked", unique = false, nullable = false, updatable = false)
    private String panMasked;

    @Column(name = "expiration_date", unique = false, nullable = false, updatable = false)
    private YearMonth expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(unique = false, nullable = false)
    private CardStatus status;

    @Setter(AccessLevel.PACKAGE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "created_at", unique = false, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", unique = false, nullable = false, updatable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void block() {
        if (status == CardStatus.BLOCKED) {
            throw new IllegalStateException("Card is already blocked");
        }
        status = CardStatus.BLOCKED;
    }

    public void activate() {
        if (status == CardStatus.ACTIVE) {
            throw new IllegalStateException("Card is already active");
        }
        if (status == CardStatus.EXPIRED) {
            throw new IllegalStateException("Expired card cannot be activated.");
        }
        status = CardStatus.ACTIVE;
    }

    public boolean isActive() {
        return status == CardStatus.ACTIVE;
    }

    public boolean isExpired() {
        return expirationDate.isBefore(YearMonth.now());
    }

    public Card(Account account,
                String panEncrypted,
                String panMasked,
                YearMonth expirationDate) {
        if (account == null)
            throw new IllegalArgumentException("Account cannot be null");

        if (panEncrypted == null || panEncrypted.isBlank())
            throw new IllegalArgumentException("PAN cannot be empty");

        if (panMasked == null || panMasked.isBlank())
            throw new IllegalArgumentException("Masked PAN cannot be empty");

        if (expirationDate == null)
            throw new IllegalArgumentException("Expiration date required");

        this.account = account;
        this.panEncrypted = panEncrypted;
        this.panMasked = panMasked;
        this.expirationDate = expirationDate;
        this.status = CardStatus.ACTIVE;
    }
}
