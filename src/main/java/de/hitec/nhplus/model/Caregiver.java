// Paketanweisung und Importe

package de.hitec.nhplus.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Die Klasse `Caregiver` repräsentiert einen Pflegekraft-Eintrag in der Anwendung.
 * Sie erweitert die `Person`-Klasse und enthält zusätzliche Informationen wie Telefonnummer und Mitarbeiter-ID.
 */
public class Caregiver extends Person {
    private final SimpleStringProperty phoneNumber;
    private SimpleLongProperty cid;
    private boolean locked;

    /**
     * Der Konstruktor erstellt ein neues Objekt der Klasse Caregiver mit den Variablen:
     *
     * @param firstName   Der Vorname des Angestellten
     * @param surname     Der Nachname des Angestellten
     * @param phoneNumber Die Telefonnummer des Angestellten
     * @param locked      Der locked Status des Caregivers
     *                    <p>
     *                    Dieser wird genutzt, wenn der Caregiver noch kein "pid" hat.
     **/
    public Caregiver(String firstName, String surname, String phoneNumber, boolean locked) {
        super(firstName, surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.locked = locked;
    }

    /**
     * Erzeugt eine neue Instanz der Klasse `Caregiver` mit den angegebenen Parametern.
     *
     * @param cid         Die Caregiver id des Pflegers.
     * @param firstName   Der Vorname der Pflegekraft.
     * @param surname     Der Nachname der Pflegekraft.
     * @param phoneNumber Die Telefonnummer der Pflegekraft.
     * @param locked      Gibt an, ob der Pflegekraft gesperrt ist.
     */
    public Caregiver(long cid, String firstName, String surname, String phoneNumber, boolean locked) {
        super(firstName, surname);
        this.cid = new SimpleLongProperty(cid);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.locked = locked;
    }

    /**
     * Gibt eine textuelle Darstellung der Pflegekraft zurück, die Informationen über Vorname, Nachname und Telefonnummer enthält.
     *
     * @return Die textuelle Darstellung der Pflegekraft.
     */
    @Override
    public String toString() {
        return "Caregiver{" + "cid=" + cid + "firstname=" + getFirstName() + "surname=" + getSurname() + ", phoneNumber=" + phoneNumber + '}';
    }

    /**
     * Gibt die Telefonnummer der Pflegekraft zurück.
     *
     * @return Die Telefonnummer der Pflegekraft.
     */
    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    /**
     * Legt die Telefonnummer der Pflegekraft fest.
     *
     * @param newValue Die neue Telefonnummer der Pflegekraft.
     */
    public void setPhoneNumber(String newValue) {
        this.phoneNumber.set(newValue);
    }

    /**
     * Gibt die Mitarbeiter-ID der Pflegekraft zurück.
     *
     * @return Die Mitarbeiter-ID der Pflegekraft.
     */
    public long getCid() {
        return cid.get();
    }

    /**
     * Gibt an, ob der Pflegekraft gesperrt ist.
     *
     * @return `true`, wenn der Pflegekraft gesperrt ist, andernfalls `false`.
     */
    public boolean getLocked() {
        return this.locked;
    }

    /**
     * Gibt den Namen der Pflegekraft zurück.
     *
     * @return Der Name der Pflegekraft im Format "Nachname, Vorname".
     */
    public String getName() {
        return this.getSurname() + ", " + this.getFirstName();
    }

    /**
     * Gibt den Login-Namen der Pflegekraft zurück.
     *
     * @return Der Login-Name der Pflegekraft im Format "Vorname, Nachname".
     */
    public String getLoginName() {
        return this.getFirstName() + "," + this.getSurname();
    }
}