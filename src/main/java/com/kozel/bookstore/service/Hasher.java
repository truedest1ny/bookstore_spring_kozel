package com.kozel.bookstore.service;

public interface Hasher {
    String generateSalt();
    String hashPassword(String password, String salt);
    String hashPassword(String password, String salt, int iterations, int keyLength);
}
