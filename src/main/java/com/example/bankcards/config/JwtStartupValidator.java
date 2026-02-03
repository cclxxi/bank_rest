package com.example.bankcards.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Validates JWT configuration on startup to prevent weak secrets in production.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtStartupValidator {

    private final JwtConfig jwtConfig;
    private final Environment environment;

    @PostConstruct
    public void validate() {
        boolean isProd = Arrays.stream(environment.getActiveProfiles())
                .anyMatch(p -> p.equalsIgnoreCase("prod") || p.equalsIgnoreCase("production"));

        if (!isProd) {
            return; // Only enforce strict validation in production
        }

        String secret = jwtConfig.getSecret();
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT secret must be provided in production environment");
        }

        // Reject known weak or placeholder secrets
        if ("defaultSecretKey".equals(secret) || secret.toLowerCase().contains("secretkey")) {
            throw new IllegalStateException("Weak JWT secret detected in production. Use a strong, random value.");
        }

        if (secret.length() < 32) {
            throw new IllegalStateException("JWT secret is too short for production. Minimum length is 32 characters.");
        }

        log.info("JWT configuration validated for production profile");
    }
}
