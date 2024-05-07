package de.hitec.nhplus.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Diese Methode initialisiert die Klasse Caregiver, ertellt relevante Variablen und eine Liste
 **/
public class Caregiver extends Person {
    private SimpleLongProperty cid;
    private SimpleStringProperty phoneNumber;
    private boolean locked;

    /**
     * Der Konstruktor erstellt ein neues Objekt der Klasse Caregiver mit den Variablen:
     * @param firstName     Der Vorname des Angestellten
     * @param surname       Der Nachname des Angestellten
     * @param phoneNumber   Die Telefonnummer des Angestellten.
     *
     * Dieser wird genutzt wenn der Caregiver noch kein "pid" hat.
     **/
    public Caregiver(String firstName, String surname, String phoneNumber, boolean locked) {
        super(firstName, surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.locked = locked;
    }

    /**
     *  Dieser Kontruktor wird genutzt wenn der Caregiver bereits ein "pid" hat und bereits existietren.
     *  Er weisst dem Angestellten diese Werte zu:
     *
     *  @param cid           Angestellten ID.
     *  @param firstName     Der Vorname des Angestellten
     *  @param surname       Der Nachname des Angestellten
     **/
    public Caregiver(long cid, String firstName, String surname, String phoneNumber, boolean locked) {
        super(firstName, surname);
        this.cid = new SimpleLongProperty(cid);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.locked = locked;
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

    public boolean getLocked() {
        return this.locked;
    }
}