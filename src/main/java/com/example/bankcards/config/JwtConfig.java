package com.example.bankcards.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    /**
     * Secret key for JWT signature
     */
    private String secret = "defaultSecretKey";

    /**
     * Token expiration time in milliseconds
     * Default: 24 hours
     */
    private long expiration = 86400000;

    /**
     * Token header name
     */
    private String header = "Authorization";

    /**
     * Token prefix
     */
    private String tokenPrefix = "Bearer ";
}
