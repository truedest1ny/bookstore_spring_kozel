package com.kozel.bookstore.service;

/**
 * An interface for a password hashing service.
 * This contract defines methods for securely hashing passwords
 * and generating cryptographic salts.
 */
public interface Hasher {

    /**
     * Generates a new, cryptographically secure salt.
     * The generated salt should be unique for each password hash to prevent rainbow table attacks.
     *
     * @return A Base64-encoded string representing the salt.
     */
    String generateSalt();

    /**
     * Hashes a password using a predefined number of iterations and key length.
     *
     * @param password The raw password string.
     * @param salt The salt string to be used for hashing.
     * @return A Base64-encoded string representing the hashed password.
     */
    String hashPassword(String password, String salt);

    /**
     * Hashes a password with a custom number of iterations and key length.
     * This method provides more control over the hashing process.
     *
     * @param password The raw password string.
     * @param salt The salt string.
     * @param iterations The number of iterations for the hashing algorithm.
     * @param keyLength The desired key length in bits.
     * @return A Base64-encoded string representing the hashed password.
     */
    String hashPassword(String password, String salt, int iterations, int keyLength);
}
