package de.hitec.nhplus.datastorage;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    void create(T t) throws SQLException; // Fügt einen Neuen Datensatz "t" in die Datenbank ein

    T read(long key) throws SQLException; // Liest Daten des der Datenbank bei der Postition des "Key" aus und dibt diese zurück.

    List<T> readAll() throws SQLException; // Liesst ganze Datenbank aus und gibt diese als Objektliste aus.

    void update(T t) throws SQLException; // Überschreibt / aktualisiert den Datenbankwert bei der Position des "Key".

    void deleteById(long key) throws SQLException; // Löscht Daten die mit der übergebenen ID verbunden sind.
}
