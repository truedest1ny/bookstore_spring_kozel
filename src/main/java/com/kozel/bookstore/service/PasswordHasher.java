package com.kozel.bookstore.service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordHasher {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static String hashPassword(String password, String salt){
        return hashPassword(password, salt, ITERATIONS, KEY_LENGTH);
    }

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private static String hashPassword(String password, String salt, int iterations, int keyLength){
        try {
            byte[] saltBytes = Base64.getDecoder().decode(salt);

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    saltBytes,
                    iterations,
                    keyLength
            );

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password: ", e);
    }
    }
}
