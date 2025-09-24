package com.nerosoft.aone.identity.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CryptographyTests {
    @Test
    void contextLoads() {
    }

    @Test
    void testAesEncrypt() throws Exception {
        String data = "Hello, World!";
        String salt = "1234567887654321";
        String encryptedData = Cryptography.AES.encrypt(data, salt);
        System.out.println("DES Encrypted Data: " + encryptedData);
        String decryptedData = Cryptography.AES.decrypt(encryptedData, salt);
        System.out.println("DES Decrypted Data: " + decryptedData);
    }

    @Test
    void testDesEncrypt() throws Exception {
        String data = "Hello, World!";
        String salt = "12345678";
        String encryptedData = Cryptography.DES.encrypt(data, salt);
        System.out.println("DES Encrypted Data: " + encryptedData);
        String decryptedData = Cryptography.DES.decrypt(encryptedData, salt);
        System.out.println("DES Decrypted Data: " + decryptedData);
    }
}
