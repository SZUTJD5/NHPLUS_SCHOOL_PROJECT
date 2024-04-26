package de.hitec.nhplus.model;

import de.hitec.nhplus.utils.DateConverter;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Patienten leben in einem Pflegeheim und werden von Pflegekräften behandelt.
 */

/**
 * Diese Methode initialisiert die Klasse Patient, ertellt relevante Variablen und eine Liste
 **/
public class Patient extends Person {
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty careLevel;
    private final SimpleStringProperty roomNumber;
    private final List<Treatment> allTreatments = new ArrayList<>();
    private SimpleLongProperty pid;

    /**
     * Der Konstruktor erstellt ein neues Objekt der Klasse Patient mit den Variablen:
     * @param firstName     Der Vorname des Patienten
     * @param surname       Der Nachname des Patienten
     * @param dateOfBirth   Das Geburtsdatum des Patienten.
     * @param careLevel     Die Pflegestufe des Patienten.
     * @param roomNumber    Die Zimmernummer des Patienten.
     *
     * Dieser wird genutzt wenn der Patient noch kein "pid" hat.
     **/
    public Patient(String firstName, String surname, LocalDate dateOfBirth, String careLevel, String roomNumber) {
        super(firstName, surname);
        this.dateOfBirth = new SimpleStringProperty(DateConverter.convertLocalDateToString(dateOfBirth));
        this.careLevel = new SimpleStringProperty(careLevel);
        this.roomNumber = new SimpleStringProperty(roomNumber);
    }

    /**
     *  Dieser Kontruktor wird genutzt wenn der Patient bereits ein "pid" hat und bereits existietren.
     *  Er weisst dem Patienten diese Werte zu:
     *
     *  @param pid           Patienten ID.
     *  @param firstName     Der Vorname des Patienten
     *  @param surname       Der Nachname des Patienten
     *  @param dateOfBirth   Das Geburtsdatum des Patienten.
     *  @param careLevel     Die Pflegestufe des Patienten.
     *  @param roomNumber    Die Zimmernummer des Patienten.
     **/
    public Patient(long pid, String firstName, String surname, LocalDate dateOfBirth, String careLevel, String roomNumber) {
        super(firstName, surname);
        this.pid = new SimpleLongProperty(pid);
        this.dateOfBirth = new SimpleStringProperty(DateConverter.convertLocalDateToString(dateOfBirth));
        this.careLevel = new SimpleStringProperty(careLevel);
        this.roomNumber = new SimpleStringProperty(roomNumber);
    }

    public long getPid() { // Gibt "pid" des Patienten aus (getter).
        return pid.get();
    }

    public SimpleLongProperty pidProperty() { // Gibt "pid" des Patienten als SimpleLongProperty aus.
        return pid;
    }

    public String getDateOfBirth() { // Gibt Geburtsdatum des Patienten aus (getter).
        return dateOfBirth.get();
    }

    /**
     * Speichert die angegebene Zeichenfolge als neues <code>birthOfDate</code>.
     *
     * @param dateOfBirth als String im folgenden Format: JJJJ-MM-TT.
     */
    public void setDateOfBirth(String dateOfBirth) { // Setzt das Geburtsdatum des Patienten auf dem des input Strings (setter).
        this.dateOfBirth.set(dateOfBirth);
    }

    public SimpleStringProperty dateOfBirthProperty() { // Gibt das Geburtsdatum des Patienten als SimpleLongProperty aus
        return dateOfBirth;
    }

    public String getCareLevel() { // Gibt das Pflegelevel des Patienten aus (getter).
        return careLevel.get();
    }

    public void setCareLevel(String careLevel) { // Setzt das Pflegelevel des Patienten auf den des Input Strings (setter).
        this.careLevel.set(careLevel);
    }

    public SimpleStringProperty careLevelProperty() { // Gibt das Pflegelevel das Patienten als SimpleLongProperty aus
        return careLevel;
    }

    public String getRoomNumber() { // Gibt die Zimmernummer des Patienten aus (getter).
        return roomNumber.get();
    }

    public void setRoomNumber(String roomNumber) { // Setzt die Zimmernummer des Patienten auf die des Input Strings (setter).
        this.roomNumber.set(roomNumber);
    }

    public SimpleStringProperty roomNumberProperty() { // Gibt die Zimmernummer des Patienten als SimpleLongPropery aus
        return roomNumber;
    }

    /**
     * Diese Methode fügt die Behandlung der treatment Tabelle hinzu falls diese noch nich in dieser Enthalten ist.
     */
    public boolean add(Treatment treatment) {
        if (this.allTreatments.contains(treatment)) {
            return false;
        }
        this.allTreatments.add(treatment);
        return true;
    }

    public String toString() { // Gibt die Daten der Klasse als String aus.
        return "Patient" + "\nMNID: " + this.pid +
                "\nFirstname: " + this.getFirstName() +
                "\nSurname: " + this.getSurname() +
                "\nBirthday: " + this.dateOfBirth +
                "\nCarelevel: " + this.careLevel +
                "\nRoomnumber: " + this.roomNumber +
                "\n";
    }
}