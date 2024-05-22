// Paketanweisung und Importe

package de.hitec.nhplus.model;

import de.hitec.nhplus.utils.DateConverter;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse `Patient` repräsentiert einen Patienten in der Anwendung.
 * Sie erweitert die `Person`-Klasse und enthält zusätzliche Informationen wie Geburtsdatum, Pflegestufe und Zimmernummer.
 */
public class Patient extends Person {
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty careLevel;
    private final SimpleStringProperty roomNumber;
    private final List<Treatment> allTreatments = new ArrayList<>();
    private SimpleLongProperty pid;
    private boolean locked;

    /**
     * Erzeugt eine neue Instanz der Klasse `Patient` mit den angegebenen Parametern.
     *
     * @param firstName   Der Vorname des Patienten.
     * @param surname     Der Nachname des Patienten.
     * @param dateOfBirth Das Geburtsdatum des Patienten.
     * @param careLevel   Die Pflegestufe des Patienten.
     * @param roomNumber  Die Zimmernummer des Patienten.
     * @param locked      Gibt an, ob der Patient gesperrt ist.
     */
    public Patient(String firstName, String surname, LocalDate dateOfBirth, String careLevel, String roomNumber, boolean locked) {
        super(firstName, surname);
        this.dateOfBirth = new SimpleStringProperty(DateConverter.convertLocalDateToString(dateOfBirth));
        this.careLevel = new SimpleStringProperty(careLevel);
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.locked = locked;
    }

    /**
     * Erzeugt eine neue Instanz der Klasse `Patient` mit den angegebenen Parametern, einschließlich der Patienten-ID.
     *
     * @param pid         Die Patienten-ID.
     * @param firstName   Der Vorname des Patienten.
     * @param surname     Der Nachname des Patienten.
     * @param dateOfBirth Das Geburtsdatum des Patienten.
     * @param careLevel   Die Pflegestufe des Patienten.
     * @param roomNumber  Die Zimmernummer des Patienten.
     * @param locked      Gibt an, ob der Patient gesperrt ist.
     */
    public Patient(long pid, String firstName, String surname, LocalDate dateOfBirth, String careLevel, String roomNumber, boolean locked) {
        super(firstName, surname);
        this.pid = new SimpleLongProperty(pid);
        this.dateOfBirth = new SimpleStringProperty(DateConverter.convertLocalDateToString(dateOfBirth));
        this.careLevel = new SimpleStringProperty(careLevel);
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.locked = locked;
    }

    /**
     * Gibt an, ob der Patient gesperrt ist.
     *
     * @return `true`, wenn der Patient gesperrt ist, andernfalls `false`.
     */
    public boolean getLocked() {
        return locked;
    }

    /**
     * Gibt die Patienten-ID zurück.
     *
     * @return Die Patienten-ID.
     */
    public long getPid() { // Gibt "pid" des Patienten aus (getter).
        return pid.get();
    }

    /**
     * Gibt das Geburtsdatum des Patienten zurück.
     *
     * @return Das Geburtsdatum des Patienten im Format "JJJJ-MM-TT".
     */
    public String getDateOfBirth() { // Gibt Geburtsdatum des Patienten aus (getter).
        return dateOfBirth.get();
    }

    /**
     * Legt das Geburtsdatum des Patienten fest.
     *
     * @param dateOfBirth Das neue Geburtsdatum des Patienten im Format "JJJJ-MM-TT".
     */
    public void setDateOfBirth(String dateOfBirth) { // Setzt das Geburtsdatum des Patienten auf dem des input Strings (setter).
        this.dateOfBirth.set(dateOfBirth);
    }

    /**
     * Gibt die Pflegestufe des Patienten zurück.
     *
     * @return Die Pflegestufe des Patienten.
     */
    public String getCareLevel() { // Gibt das Pflegelevel des Patienten aus (getter).
        return careLevel.get();
    }

    /**
     * Legt die Pflegestufe des Patienten fest.
     *
     * @param careLevel Die neue Pflegestufe des Patienten.
     */
    public void setCareLevel(String careLevel) { // Setzt das Pflegelevel des Patienten auf den des Input Strings (setter).
        this.careLevel.set(careLevel);
    }

    /**
     * Gibt die Zimmernummer des Patienten zurück.
     *
     * @return Die Zimmernummer des Patienten.
     */
    public String getRoomNumber() { // Gibt die Zimmernummer des Patienten aus (getter).
        return roomNumber.get();
    }

    /**
     * Legt die Zimmernummer des Patienten fest.
     *
     * @param roomNumber Die neue Zimmernummer des Patient
     */
    public void setRoomNumber(String roomNumber) { // Setzt die Zimmernummer des Patienten auf die des Input Strings (setter).
        this.roomNumber.set(roomNumber);
    }

    /**
     * Fügt eine Behandlung zur Liste aller Behandlungen dieses Patienten hinzu, falls sie noch nicht enthalten ist.
     *
     * @param treatment Die hinzuzufügende Behandlung.
     * @return `true`, wenn die Behandlung erfolgreich hinzugefügt wurde, andernfalls `false`.
     */
    public boolean add(Treatment treatment) {
        if (this.allTreatments.contains(treatment)) {
            return false;
        }
        this.allTreatments.add(treatment);
        return true;
    }

    /**
     * Gibt eine Zeichenfolge dar, die die Daten des Patienten repräsentiert.
     *
     * @return Eine Zeichenfolge mit den Daten des Patienten.
     */
    public String toString() { // Gibt die Daten der Klasse als String aus.
        return "Patient" + "\nMNID: " + this.pid + "\nFirstname: " + this.getFirstName() + "\nSurname: " + this.getSurname() + "\nBirthday: " + this.dateOfBirth + "\nCarelevel: " + this.careLevel + "\nRoomnumber: " + this.roomNumber + "\nLocked: " + this.locked + "\n";
    }
}