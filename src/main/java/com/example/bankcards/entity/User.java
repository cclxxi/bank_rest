package com.example.bankcards.entity;

import com.example.bankcards.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "user_id")
    private Long id;

    @Column(unique = false, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private String surname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password_hashed", unique = false, nullable = false)
    private String passwordHashed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", unique = false, nullable = false)
    private UserStatus status;

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

    public void activate() {
        if (status == UserStatus.ACTIVE) {
            throw new IllegalStateException("User is already active");
        }
        status = UserStatus.ACTIVE;
    }

    public void block() {
        if (status == UserStatus.BLOCKED) {
            throw new IllegalStateException("User is already blocked");
        }
        status = UserStatus.BLOCKED;
    }

    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }

    public static User create(
            String email,
            String name,
            String surname,
            String passwordHash,
            Role role
    ) {
        User user = new User();
        user.email = email;
        user.name = name;
        user.surname = surname;
        user.passwordHashed = passwordHash;
        user.role = role;
        return user;
    }
}
