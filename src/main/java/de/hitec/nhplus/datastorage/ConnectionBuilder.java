package de.hitec.nhplus.datastorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

/**
 * Klasse zum Verbinden von Java und der SqLite Datenbank
 */
public class ConnectionBuilder {

    private static final String DB_NAME = "nursingHome.db"; // Name der Datenbank
    private static final String URL = "jdbc:sqlite:db/" + DB_NAME; // URL zur Datenbank

    private static Connection connection; // Verbindung zur Datenbank

    /**
     * Diese Methode stellt eine Verbindung zur Datenbank her, wenn keine bestehende Verbindung vorhanden ist.
     * Die Verbindung wird dann zurückgegeben.
     *
     * @return Die Verbindung zur Datenbank.
     */
    synchronized public static Connection getConnection() {
        try {
            if (ConnectionBuilder.connection == null) {
                SQLiteConfig configuration = new SQLiteConfig();
                configuration.enforceForeignKeys(true);
                ConnectionBuilder.connection = DriverManager.getConnection(URL, configuration.toProperties());
            }
        } catch (SQLException exception) {
            // Fehlermeldung bei Verbindungsfehler
            System.out.println("Verbindung zur Datenbank konnte nicht aufgebaut werden!");
            System.setErr(System.err);
        }
        return ConnectionBuilder.connection;
    }

    /**
     * Diese Methode beendet die Verbindung zur Datenbank, falls eine bestehende Verbindung vorhanden ist.
     */
    synchronized public static void closeConnection() {
        try {
            if (ConnectionBuilder.connection != null) {
                // Schließen der Verbindung und Zurücksetzen des Verbindungsobjekts
                ConnectionBuilder.connection.close();
                ConnectionBuilder.connection = null;
            }
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
    }
}