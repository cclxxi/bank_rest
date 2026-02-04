package com.example.bankcards.util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PanEncryptor {

    private final TextEncryptor textEncryptor;

    public String encrypt(String pan) {
        return textEncryptor.encrypt(pan);
    }

    public String decrypt(String encryptedPan) {
        return textEncryptor.decrypt(encryptedPan);
    }
}
