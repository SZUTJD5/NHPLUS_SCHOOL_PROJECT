package de.hitec.nhplus.model;

public class ActiveAcount extends Person {
    private static ActiveAcount instance;

    // Private constructor to prevent instantiation
    private ActiveAcount(String fullName) {
        super(extractFirstName(fullName), extractSurname(fullName));
    }

    // Method to extract the first name from the full name
    private static String extractFirstName(String fullName) {
        String[] parts = fullName.split(",");
        return parts.length > 0 ? parts[0].trim() : "";
    }

    // Method to extract the surname from the full name
    private static String extractSurname(String fullName) {
        String[] parts = fullName.split(",");
        return parts.length > 1 ? parts[1].trim() : "";
    }

    // Public method to provide access to the single instance
    public static ActiveAcount getInstance(String fullName) {
        if (instance == null) {
            instance = new ActiveAcount(fullName);
        }
        return instance;
    }

    public String getName() {
        return this.getSurname() + ", " + this.getFirstName();
    }
}
