package de.hitec.nhplus.datastorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

public class ConnectionBuilder {

    private static final String DB_NAME = "nursingHome.db";
    private static final String URL = "jdbc:sqlite:db/" + DB_NAME;

    private static Connection connection; // Verbindung zur Datenbank

    /**
     *  Diese Methode verbindet sich mit der Datenbank wenn bereits keine andere Verbindung besteht.
     *  Die Verbindung wird dann zurückgegeben.
     */
    synchronized public static Connection getConnection() {
        try {
            if (ConnectionBuilder.connection == null) {
                SQLiteConfig configuration = new SQLiteConfig();
                configuration.enforceForeignKeys(true);
                ConnectionBuilder.connection = DriverManager.getConnection(URL, configuration.toProperties());
            }
        } catch (SQLException exception) {
            System.out.println("Verbindung zur Datenbank konnte nicht aufgebaut werden!");
            exception.printStackTrace();
        }
        return ConnectionBuilder.connection;
    }

    /**
     * Diese Methode beendet die Verbindung zur Datenbank, falls eine besteht.
     */
    synchronized public static void closeConnection() {
        try {
            if (ConnectionBuilder.connection != null) {
                ConnectionBuilder.connection.close();
                ConnectionBuilder.connection = null;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
