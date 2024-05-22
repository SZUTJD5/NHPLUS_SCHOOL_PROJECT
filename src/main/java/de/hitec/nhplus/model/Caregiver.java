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
     * @param phoneNumber Die Telefonnummer des Angestellten.
     *                    <p>
     *                    Dieser wird genutzt wenn der Caregiver noch kein "pid" hat.
     **/
    public Caregiver(String firstName, String surname, String phoneNumber, boolean locked) {
        super(firstName, surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.locked = locked;
    }

    /**
     * Erzeugt eine neue Instanz der Klasse `Caregiver` mit den angegebenen Parametern.
     *
     * @param firstName   Der Vorname des Pflegekrafts.
     * @param surname     Der Nachname des Pflegekrafts.
     * @param phoneNumber Die Telefonnummer des Pflegekrafts.
     * @param locked      Gibt an, ob der Pflegekraft gesperrt ist.
     */
    public Caregiver(long cid, String firstName, String surname, String phoneNumber, boolean locked) {
        super(firstName, surname);
        this.cid = new SimpleLongProperty(cid);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.locked = locked;
    }

    /**
     * Gibt eine textuelle Darstellung des Pflegekrafts zurück, die Informationen über Vorname, Nachname und Telefonnummer enthält.
     *
     * @return Die textuelle Darstellung des Pflegekrafts.
     */
    @Override
    public String toString() {
        return "Caregiver{" + "cid=" + cid + "firstname=" + getFirstName() + "surname=" + getSurname() + ", phoneNumber=" + phoneNumber + '}';
    }

    /**
     * Gibt die Telefonnummer des Pflegekrafts zurück.
     *
     * @return Die Telefonnummer des Pflegekrafts.
     */
    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    /**
     * Legt die Telefonnummer des Pflegekrafts fest.
     *
     * @param newValue Die neue Telefonnummer des Pflegekrafts.
     */
    public void setPhoneNumber(String newValue) {
        this.phoneNumber.set(newValue);
    }

    /**
     * Gibt die Mitarbeiter-ID des Pflegekrafts zurück.
     *
     * @return Die Mitarbeiter-ID des Pflegekrafts.
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
     * Gibt den Namen des Pflegekrafts zurück.
     *
     * @return Der Name des Pflegekrafts im Format "Nachname, Vorname".
     */
    public String getName() {
        return this.getSurname() + ", " + this.getFirstName();
    }

    /**
     * Gibt den Login-Namen des Pflegekrafts zurück.
     *
     * @return Der Login-Name des Pflegekrafts im Format "Vorname,Nachname".
     */
    public String getLoginName() {
        return this.getFirstName() + "," + this.getSurname();
    }
}