package de.hitec.nhplus.model;

import de.hitec.nhplus.utils.DateConverter;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Angestellten leben in einem Pflegeheim und werden von Pflegekräften behandelt.
 */

/**
 * Diese Methode initialisiert die Klasse Caregiver, ertellt relevante Variablen und eine Liste
 **/
public class Caregiver extends Person {
    private SimpleLongProperty cid;
    private SimpleStringProperty phoneNumber;

    /**
     * Der Konstruktor erstellt ein neues Objekt der Klasse Caregiver mit den Variablen:
     * @param firstName     Der Vorname des Angestellten
     * @param surname       Der Nachname des Angestellten
     * @param phoneNumber   Die Telefonnummer des Angestellten.
     *
     * Dieser wird genutzt wenn der Caregiver noch kein "pid" hat.
     **/
    public Caregiver(String firstName, String surname, String phoneNumber) {
        super(firstName, surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    /**
     *  Dieser Kontruktor wird genutzt wenn der Caregiver bereits ein "pid" hat und bereits existietren.
     *  Er weisst dem Angestellten diese Werte zu:
     *
     *  @param cid           Angestellten ID.
     *  @param firstName     Der Vorname des Angestellten
     *  @param surname       Der Nachname des Angestellten
     **/
    public Caregiver(long cid, String firstName, String surname, String phoneNumber) {
        super(firstName, surname);
        this.cid = new SimpleLongProperty(cid);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    @Override
    public String toString() {
        return "Caregiver{" +
                "cid=" + cid +
                "firstname=" + getFirstName() +
                "surname=" + getSurname() +
                ", phoneNumber=" + phoneNumber +
                '}';
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public long getCid() {
        return cid.get();
    }

    public void setPhoneNumber(String newValue) {
        this.phoneNumber.set(newValue);
    }
}