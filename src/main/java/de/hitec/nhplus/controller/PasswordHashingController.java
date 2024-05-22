// Paketanweisung und Importe
package de.hitec.nhplus.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;
import java.util.Arrays;


/**
 * Die Klasse <code>PasswordHashingController</code> ist verantwortlich für das Hashen und Verifizieren von Passwörtern.
 * Sie verwendet den PBKDF2-Algorithmus mit einem HMAC-SHA1-Hasher für eine sichere Passwort-Hashing-Funktionalität.
 */
public class PasswordHashingController {

    /**
     * Hashes a password using PBKDF2 algorithm with HMAC-SHA1 hasher.
     *
     * @param password The password to be hashed
     * @return A concatenated string of salt and hash, separated by ':'
     */
    public String hashPassword(String password) {

        try {
            // Generiert ein zufälliges Salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // Spezifiziert die Schlüsselspezifikation
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            // Generiert den Hash
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // Konvertiert den Hash und das Salt zu Base64 für die Speicherung
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);

            // Gibt den konkatenierten String von Salt und Hash zurück
            return saltBase64 + ":" + hashBase64;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Überprüft, ob das eingegebene Passwort dem gespeicherten gehashten Passwort entspricht.
     *
     * @param inputPassword     Das zu überprüfende Passwort
     * @param storedSaltAndHash Der gespeicherte Salt- und Hash-Wert als konkatenierter String
     * @return True, wenn die Passwörter übereinstimmen, sonst False
     */
    public boolean verifyPassword(String inputPassword, String storedSaltAndHash) {

        // Teilt storedSaltAndHash in Salt- und Hash-Teile auf
        String[] parts = storedSaltAndHash.split(":");
        if (parts.length != 2) {
            // Teilt storedSaltAndHash in Salt- und Hash-Teile auf
            return false;
        }

        // Dekodiert das Base64-kodierte Salt und den Hash
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] storedHash = Base64.getDecoder().decode(parts[1]);

        try {
            // Generiert den Hash für das eingegebene Passwort unter Verwendung des gleichen Salzes und der gleichen Parameter
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