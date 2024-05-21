package de.hitec.nhplus.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;
import java.util.Arrays;

public class PasswordHashingController {

    public String hashPassword(String password) {

        try {
            // Generate a random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // Specify the key specification
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            // Generate the hash
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // Convert the hash and salt to Base64 for storage
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);

            // Return the concatenated string of salt and hash
            return saltBase64 + ":" + hashBase64;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean verifyPassword(String inputPassword, String storedSaltAndHash) {

        // Split the storedSaltAndHash into salt and hash parts
        String[] parts = storedSaltAndHash.split(":");
        if (parts.length != 2) {
            // Invalid format
            return false;
        }

        // Decode the Base64 encoded salt and hash
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] storedHash = Base64.getDecoder().decode(parts[1]);

        try {
            // Generate the hash for the input password using the same salt and parameters
            KeySpec spec = new PBEKeySpec(inputPassword.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] inputHash = factory.generateSecret(spec).getEncoded();

            return Arrays.equals(storedHash, inputHash);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
