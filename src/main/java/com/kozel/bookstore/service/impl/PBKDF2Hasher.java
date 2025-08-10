package com.kozel.bookstore.service.impl;

import com.kozel.bookstore.service.Hasher;
import com.kozel.bookstore.service.annotation.SecuredLogging;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Service
@SecuredLogging
public class PBKDF2Hasher implements Hasher {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public String hashPassword(String password, String salt){
        return hashPassword(password, salt, ITERATIONS, KEY_LENGTH);
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String hashPassword(String password, String salt, int iterations, int keyLength){
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
