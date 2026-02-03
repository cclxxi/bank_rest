package com.example.bankcards.entity;

import com.example.bankcards.enums.TransactionStatus;
import com.example.bankcards.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "transactions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id", nullable = false)
    private Account toAccount;

    @Enumerated(EnumType.STRING)
    @Column(unique = false, nullable = false, updatable = false)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(unique = false, nullable = false, updatable = false)
    private TransactionType type;

    @Column(unique = false,
            nullable = false,
            updatable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal amount;

    @Column(unique = true, nullable = false, updatable = false)
    private String reference;

    @Column(name = "created_at", unique = false, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", unique = false, nullable = true, updatable = true)
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Transaction(Account from,
                       Account to,
                       BigDecimal amount,
                       String reference,
                       TransactionType type) {

        if (from == null || to == null)
            throw new IllegalArgumentException("Accounts required");

        if (from.getId().equals(to.getId()))
            throw new IllegalArgumentException("Cannot transfer to same account");

        if (amount == null || amount.signum() <= 0)
            throw new IllegalArgumentException("Amount must be positive");
        if (type == null)
            throw new IllegalArgumentException("Type required");

        this.type = type;

        this.fromAccount = from;
        this.toAccount = to;
        this.amount = amount;
        this.reference = reference;
        this.status = TransactionStatus.CREATED;
    }

    public boolean isCreated() {
        return status == TransactionStatus.CREATED;
    }

    public boolean isProcessing() {
        return status == TransactionStatus.PROCESSING;
    }

    public boolean isSuccess() {
        return status == TransactionStatus.SUCCESS;
    }

    public boolean isFailed() {
        return status == TransactionStatus.FAILED;
    }

    public void markProcessing() {
        if (!isCreated())
            throw new IllegalStateException("Transaction is already processed");
        status = TransactionStatus.PROCESSING;
    }

    public void markSuccess() {
        if (isSuccess())
            throw new IllegalStateException("Transaction is already successful");
        if (!isProcessing())
            throw new IllegalStateException("Transaction is not processed yet");
        if (isFailed())
            throw new IllegalStateException("Failed transaction cannot succeed");
        status = TransactionStatus.SUCCESS;
    }

    public void markFailed() {
        if (isFailed())
            throw new IllegalStateException("Transaction is already failed");
        if (!isProcessing())
            throw new IllegalStateException("Transaction is not processed yet");
        if (isSuccess())
            throw new IllegalStateException("Transaction is successful");
        status = TransactionStatus.FAILED;
    }
}
