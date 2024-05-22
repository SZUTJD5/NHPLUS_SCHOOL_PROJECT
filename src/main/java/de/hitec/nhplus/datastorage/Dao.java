package de.hitec.nhplus.datastorage;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface zum Interagieren mit der Dantenbank
 *
 * @param <T> Typ der Dantenbank
 */
public interface Dao<T> {

    /**
     * Fügt einen neuen Datensatz in die Datenbank ein.
     *
     * @param t Das einzufügende Objekt.
     * @throws SQLException bei SQL-Fehlern.
     */
    void create(T t) throws SQLException;

    /**
     * Liest einen Datensatz aus der Datenbank anhand des Schlüssels.
     *
     * @param key Der Schlüssel, um den Datensatz zu identifizieren.
     * @return Das gelesene Objekt.
     * @throws SQLException bei SQL-Fehlern.
     */
    T read(long key) throws SQLException;

    /**
     * Aktualisiert einen bestehenden Datensatz in der Datenbank.
     *
     * @param t Das zu aktualisierende Objekt.
     * @throws SQLException bei SQL-Fehlern.
     */
    void update(T t) throws SQLException;

    /**
     * Liest alle Datensätze aus der Datenbank und gibt sie als Liste zurück.
     *
     * @return Eine Liste mit allen Datensätzen.
     * @throws SQLException bei SQL-Fehlern.
     */
    List<T> readAll() throws SQLException;
}