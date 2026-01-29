package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = false, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private String surname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = false, nullable = false)
    private String passwordHash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Account> accounts = new HashSet<>();

    @Column(name = "reset_token", nullable = true, unique = true)
    private String resetToken;

    @Column(name = "reset_token_expiry", nullable = true, unique = false)
    private LocalDateTime resetTokenExpiry;

    @Column(name = "created_at", unique = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", unique = false, nullable = false, updatable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
