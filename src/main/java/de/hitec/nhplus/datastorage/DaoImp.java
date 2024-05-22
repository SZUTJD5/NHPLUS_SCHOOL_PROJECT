package de.hitec.nhplus.datastorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DaoImp<T> implements Dao<T> {
    protected Connection connection;

    public DaoImp(Connection connection) {
        this.connection = connection;
    }

    /**
     * Fügt einen neuen Datensatz in die Datenbank ein.
     *
     * @param t Das einzufügende Objekt.
     * @throws SQLException bei SQL-Fehlern.
     */
    @Override
    public void create(T t) throws SQLException {
        getCreateStatement(t).executeUpdate();
    }

    /**
     * Liest einen Datensatz aus der Datenbank anhand des übergebenen Schlüssels (key).
     *
     * @param key Der Schlüssel des zu lesenden Datensatzes.
     * @return Das gelesene Objekt oder null, falls kein Datensatz gefunden wurde.
     * @throws SQLException bei SQL-Fehlern.
     */
    @Override
    public T read(long key) throws SQLException {
        T object = null;
        ResultSet result = getReadByIDStatement(key).executeQuery();
        if (result.next()) {
            object = getInstanceFromResultSet(result);
        }
        return object;
    }

    /**
     * Liest alle Datensätze aus der Datenbank.
     *
     * @return Eine Liste mit allen gelesenen Objekten.
     * @throws SQLException bei SQL-Fehlern.
     */
    @Override
    public List<T> readAll() throws SQLException {
        return getListFromResultSet(getReadAllStatement().executeQuery());
    }

    /**
     * Aktualisiert einen vorhandenen Datensatz in der Datenbank.
     *
     * @param t Das zu aktualisierende Objekt.
     * @throws SQLException bei SQL-Fehlern.
     */
    @Override
    public void update(T t) throws SQLException {
        getUpdateStatement(t).executeUpdate();
    }

    /**
     * Gibt eine Instanz von ResultSet aus.
     *
     * @param set Das ResultSet, das die Daten enthält.
     * @return Das erstellte Objekt.
     * @throws SQLException bei SQL-Fehlern.
     */
    protected abstract T getInstanceFromResultSet(ResultSet set) throws SQLException;

    /**
     * Wandelt einen ResultSet in eine Liste von Objekten vom Typ T um.
     *
     * @param set Das ResultSet, das die Daten enthält.
     * @return Eine Liste mit den erstellten Objekten.
     * @throws SQLException bei SQL-Fehlern.
     */
    protected abstract ArrayList<T> getListFromResultSet(ResultSet set) throws SQLException;

    /**
     * Gibt ein PreparedStatement zurück, das verwendet wird, um einen neuen Datensatz in der Datenbank zu erstellen.
     *
     * @param t Das einzufügende Objekt.
     * @return Das erstellte PreparedStatement.
     * @throws SQLException bei SQL-Fehlern.
     */
    protected abstract PreparedStatement getCreateStatement(T t) throws SQLException;

    /**
     * Gibt ein PreparedStatement zurück, das verwendet wird, um einen Datensatz aus der Datenbank anhand des übergebenen Schlüssels zu lesen.
     *
     * @param key Der Schlüssel des zu lesenden Datensatzes.
     * @return Das erstellte PreparedStatement.
     * @throws SQLException bei SQL-Fehlern.
     */
    protected abstract PreparedStatement getReadByIDStatement(long key) throws SQLException;

    /**
     * Gibt ein PreparedStatement zurück, das verwendet wird, um alle Datensätze aus der Datenbank zu lesen.
     *
     * @return Das erstellte PreparedStatement.
     * @throws SQLException bei SQL-Fehlern.
     */
    protected abstract PreparedStatement getReadAllStatement() throws SQLException;

    /**
     * Gibt ein PreparedStatement zurück, das verwendet wird, um einen vorhandenen Datensatz in der Datenbank zu aktualisieren.
     *
     * @param t Das zu aktualisierende Objekt.
     * @return Das erstellte PreparedStatement.
     * @throws SQLException bei SQL-Fehlern.
     */
    protected abstract PreparedStatement getUpdateStatement(T t) throws SQLException;
}