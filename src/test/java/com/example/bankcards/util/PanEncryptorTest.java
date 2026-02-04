package com.example.bankcards.util;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import static org.junit.jupiter.api.Assertions.*;

class PanEncryptorTest {

    private final TextEncryptor textEncryptor = Encryptors.text("password", "deadbeef");
    private final PanEncryptor panEncryptor = new PanEncryptor(textEncryptor);

    @Test
    void testEncryptionAndDecryption() {
        String pan = "1234567812345678";
        
        String encrypted = panEncryptor.encrypt(pan);
        assertNotNull(encrypted);
        assertNotEquals(pan, encrypted);
        
        String decrypted = panEncryptor.decrypt(encrypted);
        assertEquals(pan, decrypted);
    }
}
