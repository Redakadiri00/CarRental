package com.CarRentalProject.CarRental.Config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The PasswordHashing class provides methods for hashing passwords and 
 * verifying hashed passwords using the BCrypt hashing algorithm.
 */
public class PasswordHashing {
    /**
     * Hashes a plain text password using BCrypt hashing algorithm.
     *
     * @param plainPassword the plain text password to be hashed
     * @return the hashed password as a String
     */
    public static String hashPassword(String plainPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(plainPassword);
    }

    /**
     * Verifies if the provided plain text password matches the hashed password.
     *
     * @param plainPassword the plain text password to be checked
     * @param hashedPassword the hashed password to be compared against
     * @return true if the plain text password matches the hashed password, false otherwise
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(plainPassword, hashedPassword);
    }
}
