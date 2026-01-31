package com.example.bankcards.entity;

import com.example.bankcards.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "accounts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    private Long id;

    @Column(unique = true, nullable = false)
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = false, nullable = false)
    private User user;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Set<Card> cards = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(unique = false, nullable = false)
    private AccountStatus status;

    @Column(unique = false,
            nullable = false,
            precision = 19,
            scale = 2)
    private BigDecimal balance;

    @Version
    private Long version;

    @Column(name = "created_at", unique = false, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",unique = false, nullable = false, updatable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Account(User user, String number) {
        this.user = user;
        this.number = number;
        this.balance = BigDecimal.ZERO;
        this.status = AccountStatus.ACTIVE;
    }

    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        if (!isActive()) {
            throw new IllegalStateException("Account is not active");
        }
        balance = balance.subtract(amount);
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        balance = balance.add(amount);
    }

    public void addCard(Card card) {

        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null");
        }

        cards.add(card);
        card.setAccount(this);
    }

    public boolean isActive() {
        return status == AccountStatus.ACTIVE;
    }

    public void close() {
        if (balance.compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalStateException("Cannot close account with non-zero balance");
        }
        this.status = AccountStatus.CLOSED;
    }

    public void block() {
        if (status == AccountStatus.BLOCKED) {
            throw new IllegalStateException("Account is already blocked");
        }
        this.status = AccountStatus.BLOCKED;
    }
}
