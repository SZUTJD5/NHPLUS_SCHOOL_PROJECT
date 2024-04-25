// Paketanweisung und Importe
package de.hitec.nhplus.model;

import javafx.beans.property.SimpleStringProperty;

// Abstrakte Klasse Person
public abstract class Person {

    // Eigenschaften der Person als SimpleStringProperty
    private final SimpleStringProperty firstName; // Vorname
    private final SimpleStringProperty surname; // Nachname

    // Konstruktor der Klasse Person
    public Person(String firstName, String surname) {
        // Initialisierung der Vorname- und Nachname-Eigenschaften mit den übergebenen Werten
        this.firstName = new SimpleStringProperty(firstName);
        this.surname = new SimpleStringProperty(surname);
    }

    // Getter für den Vorname
    public String getFirstName() {
        return firstName.get();
    }

    // Getter für den Vorname als SimpleStringProperty
    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    // Setter für den Vorname
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    // Getter für den Nachname
    public String getSurname() {
        return surname.get();
    }

    // Getter für den Nachname als SimpleStringProperty
    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    // Setter für den Nachname
    public void setSurname(String surname) {
        this.surname.set(surname);
    }
}
