// Paketanweisung und Importe
package de.hitec.nhplus.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Die abstrakte Klasse `Person` repräsentiert eine allgemeine Person mit Vorname und Nachname.
 */
public abstract class Person {

    // Eigenschaften der Person als SimpleStringProperty
    private final SimpleStringProperty firstName; // Vorname
    private final SimpleStringProperty surname; // Nachname

    /**
     * Konstruktor der Klasse `Person`.
     *
     * @param firstName Der Vorname der Person.
     * @param surname   Der Nachname der Person.
     */
    public Person(String firstName, String surname) {
        // Initialisierung der Vorname- und Nachname-Eigenschaften mit den übergebenen Werten
        this.firstName = new SimpleStringProperty(firstName);
        this.surname = new SimpleStringProperty(surname);
    }

    /**
     * Gibt den Vornamen der Person zurück.
     *
     * @return Der Vorname der Person.
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * Setzt den Vornamen der Person.
     *
     * @param firstName Der neue Vorname der Person.
     */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    /**
     * Gibt den Vornamen der Person als SimpleStringProperty zurück.
     *
     * @return Der Vorname der Person als SimpleStringProperty.
     */
    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Gibt den Nachnamen der Person zurück.
     *
     * @return Der Nachname der Person.
     */
    public String getSurname() {
        return surname.get();
    }

    /**
     * Setzt den Nachnamen der Person.
     *
     * @param surname Der neue Nachname der Person.
     */
    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    /**
     * Gibt den Nachnamen der Person als SimpleStringProperty zurück.
     *
     * @return Der Nachname der Person als SimpleStringProperty.
     */
    public SimpleStringProperty surnameProperty() {
        return surname;
    }
}