package de.hitec.nhplus.datastorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DaoImp<T> implements Dao<T> {
    protected Connection connection;

    public DaoImp(Connection connection) {
        this.connection = connection;
    }

    @Override // Diese Methode erstellt einen neuen Datensatz in der Datenbank.
    public void create(T t) throws SQLException {
        getCreateStatement(t).executeUpdate();
    }

    @Override // Diese Methode liest einen Datensatz aus der Datenbank anhand des übergebenen Schlüssels (key).
    public T read(long key) throws SQLException {
        T object = null;
        ResultSet result = getReadByIDStatement(key).executeQuery();
        if (result.next()) {
            object = getInstanceFromResultSet(result);
        }
        return object;
    }

    @Override // Diese Methode liest alle Datensätze aus der Datenbank.
    public List<T> readAll() throws SQLException {
        return getListFromResultSet(getReadAllStatement().executeQuery());
    }

    @Override // Diese Methode aktualisiert einen vorhandenen Datensatz in der Datenbank.
    public void update(T t) throws SQLException {
        getUpdateStatement(t).executeUpdate();
    }

    @Override // Diese Methode löscht einen Datensatz aus der Datenbank anhand des übergebenen Schlüssels (key).
    public void deleteById(long key) throws SQLException {
        getDeleteStatement(key).executeUpdate();
    }

    // Diese abstrakte Methode wandelt einen ResultSet in ein Objekt vom Typ T um.
    protected abstract T getInstanceFromResultSet(ResultSet set) throws SQLException;

    // Diese abstrakte Methode wandelt einen ResultSet in eine Liste von Objekten vom Typ T um.
    protected abstract ArrayList<T> getListFromResultSet(ResultSet set) throws SQLException;

    // Diese abstrakte Methode gibt ein PreparedStatement zurück, das verwendet wird, um einen neuen Datensatz in der Datenbank zu erstellen.
    protected abstract PreparedStatement getCreateStatement(T t);

    // Diese abstrakte Methode gibt ein PreparedStatement zurück, das verwendet wird, um einen Datensatz aus der Datenbank anhand des übergebenen Schlüssels zu lesen.
    protected abstract PreparedStatement getReadByIDStatement(long key);

    // Diese abstrakte Methode gibt ein PreparedStatement zurück, das verwendet wird, um alle Datensätze aus der Datenbank zu lesen.
    protected abstract PreparedStatement getReadAllStatement();

    // Diese abstrakte Methode gibt ein PreparedStatement zurück, das verwendet wird, um einen vorhandenen Datensatz in der Datenbank zu aktualisieren.
    protected abstract PreparedStatement getUpdateStatement(T t);

    // Diese abstrakte Methode gibt ein PreparedStatement zurück, das verwendet wird, um einen Datensatz aus der Datenbank anhand des übergebenen Schlüssels zu löschen.
    protected abstract PreparedStatement getDeleteStatement(long key);
}
