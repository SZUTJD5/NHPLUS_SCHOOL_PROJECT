package de.hitec.nhplus.model;

/**
 * Die Klasse <code>ActiveAcount</code> repräsentiert den Account des angemeldeten Nutzers und erbt von der Klasse <code>Person</code>.
 * Es wird als Singleton implementiert, sodass nur eine Instanzversion der Klasse zu einer Zeit existiert.
 */
public class ActiveAcount extends Person {

    /**
     * <code>ActiveAcount</code> wird als Singleton implementiert.
     */
    private static ActiveAcount instance;

    /**
     * Ein privater Konstruktor, um die Instanziierung der Klasse von außen zu verhindern.
     *
     * @param fullName Vollständiger Name des Benutzers.
     */
    private ActiveAcount(String fullName) {
        super(extractFirstName(fullName), extractSurname(fullName));
    }

    /**
     * Methode, um den Vornamen aus dem vollständigen Namen zu extrahieren.
     *
     * @param fullName Der vollständige Name des Benutzers.
     * @return Vorname des Benutzers.
     */
    private static String extractFirstName(String fullName) {
        String[] parts = fullName.split(",");
        return parts.length > 0 ? parts[0].trim() : "";
    }

    /**
     * Methode, um den Nachnamen aus dem vollständigen Namen zu extrahieren.
     *
     * @param fullName Der vollständige Name des Benutzers.
     * @return Nachname des Benutzers.
     */
    private static String extractSurname(String fullName) {
        String[] parts = fullName.split(",");
        return parts.length > 1 ? parts[1].trim() : "";
    }

    /**
     * Öfentliche Methode, um Zugriff auf die Instanz der Klasse zu erhalten.
     * Wenn keine Instanz vorhanden ist, wird eine neue erstellt.
     *
     * @param fullName Vollständiger Name des Benutzers.
     * @return Die einzige Instanz der Klasse <code>ActiveAcount</code>.
     */
    public static ActiveAcount getInstance(String fullName) {
        if (instance == null) {
            instance = new ActiveAcount(fullName);
        }
        return instance;
    }

    /**
     * Methode, um den Namen des Benutzers zu erhalten.
     *
     * @return String des Namen des Benutzers in der Form "Nachname, Vorname".
     */
    public String getName() {
        return this.getSurname() + ", " + this.getFirstName();
    }
}