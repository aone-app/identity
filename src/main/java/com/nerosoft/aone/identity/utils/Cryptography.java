package com.nerosoft.aone.identity.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;

public class Cryptography {
    static {
        // 注册 BouncyCastle 提供者
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * DES 加密解密工具类
     */
    public static class DES {
        private static final String TRANSFORMATION = "DES/CBC/PKCS5Padding";

        public static String encrypt(String data, String salt) throws Exception {
            DESKeySpec desKeySpec = new DESKeySpec(salt.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec iv = new IvParameterSpec(salt.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedData);
        }

        public static String decrypt(String data, String salt) throws Exception {
            DESKeySpec desKeySpec = new DESKeySpec(salt.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec iv = new IvParameterSpec(salt.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

            byte[] decodedData = Base64.getDecoder().decode(data);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData, StandardCharsets.UTF_8);
        }

    }

    /**
     * AES 加密解密工具类
     */
    public static class AES {
        /**
         * AES CBC PKCS7 加密
         */
        public static String encrypt(String data, String salt) throws Exception {
            SecretKeySpec secretKeySpec = new SecretKeySpec(salt.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedData);
        }

        /**
         * AES CBC PKCS7 解密
         */
        public static String decrypt(String data, String salt) throws Exception {
            SecretKeySpec secretKeySpec = new SecretKeySpec(salt.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] decodedData = Base64.getDecoder().decode(data);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData, StandardCharsets.UTF_8);
        }
    }
}

