package de.hitec.nhplus.utils;

import de.hitec.nhplus.datastorage.ConnectionBuilder;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.PatientDao;
import de.hitec.nhplus.datastorage.TreatmentDao;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static de.hitec.nhplus.utils.DateConverter.convertStringToLocalDate;
import static de.hitec.nhplus.utils.DateConverter.convertStringToLocalTime;

/**
 * Der Aufruf einer statischen Klasse stellt statische Methoden zum Einrichten und Löschen der Datenbank bereit. Es verwendet die Klasse ConnectionBuilder
 * und dessen Pfad zum Aufbau der Verbindung zur Datenbank. Die Klasse ist ausführbar. Durch Ausführen der Klasse wird erstellt
 * stellt eine Verbindung zur Datenbank her und ruft setUpDb() auf, um die Datenbank zu löschen, eine saubere Datenbank aufzubauen und die Datenbank zu füllen
 * Datenbank mit einigen Testdaten.
 */
public class SetUpDB {

    /**
     * Diese Methode löscht die Datenbank durch Löschen der Tabellen. Dann ruft die Methode DDL-Anweisungen auf, um sie aufzubauen
     * Scratch- und DML-Anweisungen zum Füllen der Datenbank mit hartcodierten Testdaten.
     */
    public static void setUpDb() { // Ablauf der Klasse, ruft die funktionen auf.
        Connection connection = ConnectionBuilder.getConnection(); // Stellt verbindung zur Datenbank her
        SetUpDB.wipeDb(connection);
        SetUpDB.setUpTablePatient(connection);
        SetUpDB.setUpTableTreatment(connection);
        SetUpDB.setUpPatients();
        SetUpDB.setUpTreatments();
    }

    /**
     * Löscht / leert vorhandene Datenbanken patient und tratment
     **/
    public static void wipeDb(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE patient");
            statement.execute("DROP TABLE treatment");
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     *  Erstellt neue Tabelle patient falls keine vorhanden ist, mit den gelisteten Spalten.
     **/
    private static void setUpTablePatient(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS patient (" +
                "   pid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   firstname TEXT NOT NULL, " +
                "   surname TEXT NOT NULL, " +
                "   dateOfBirth TEXT NOT NULL, " +
                "   carelevel TEXT NOT NULL, " +
                "   roomnumber TEXT NOT NULL " +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Erstellt neue Tabelle treatment falls keine vorhanden ist, mit den gelisteten Spalten.
     **/
    private static void setUpTableTreatment(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS treatment (" +
                "   tid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   pid INTEGER NOT NULL, " +
                "   treatment_date TEXT NOT NULL, " +
                "   begin TEXT NOT NULL, " +
                "   end TEXT NOT NULL, " +
                "   description TEXT NOT NULL, " +
                "   remark TEXT NOT NULL," +
                "   FOREIGN KEY (pid) REFERENCES patient (pid) ON DELETE CASCADE " +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Diese Funktion erstellt mehrere Objekte der Klasse Patient und fügt sie der Tabelle patient hinzu.
     **/
    private static void setUpPatients() {
        try {
            PatientDao dao = DaoFactory.getDaoFactory().createPatientDAO();
            dao.create(new Patient("Seppl", "Herberger", convertStringToLocalDate("1945-12-01"), "4", "202"));
            dao.create(new Patient("Martina", "Gerdsen", convertStringToLocalDate("1954-08-12"), "5", "010"));
            dao.create(new Patient("Gertrud", "Franzen", convertStringToLocalDate("1949-04-16"), "3", "002"));
            dao.create(new Patient("Ahmet", "Yilmaz", convertStringToLocalDate("1941-02-22"), "3", "013"));
            dao.create(new Patient("Hans", "Neumann", convertStringToLocalDate("1955-12-12"), "2", "001"));
            dao.create(new Patient("Elisabeth", "Müller", convertStringToLocalDate("1958-03-07"), "5", "110"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Diese Funktion erstellt mehrere Objekte der Klasse Treatment und fügt sie der Tabelle treatment hinzu.
     **/
    private static void setUpTreatments() {
        try {
            TreatmentDao dao = DaoFactory.getDaoFactory().createTreatmentDao();
            dao.create(new Treatment(1, 1, convertStringToLocalDate("2023-06-03"), convertStringToLocalTime("11:00"), convertStringToLocalTime("15:00"), "Gespräch", "Der Patient hat enorme Angstgefühle und glaubt, er sei überfallen worden. Ihm seien alle Wertsachen gestohlen worden.\nPatient beruhigt sich erst, als alle Wertsachen im Zimmer gefunden worden sind."));
            dao.create(new Treatment(2, 1, convertStringToLocalDate("2023-06-05"), convertStringToLocalTime("11:00"), convertStringToLocalTime("12:30"), "Gespräch", "Patient irrt auf der Suche nach gestohlenen Wertsachen durch die Etage und bezichtigt andere Bewohner des Diebstahls.\nPatient wird in seinen Raum zurückbegleitet und erhält Beruhigungsmittel."));
            dao.create(new Treatment(3, 2, convertStringToLocalDate("2023-06-04"), convertStringToLocalTime("07:30"), convertStringToLocalTime("08:00"), "Waschen", "Patient mit Waschlappen gewaschen und frisch angezogen. Patient gewendet."));
            dao.create(new Treatment(4, 1, convertStringToLocalDate("2023-06-06"), convertStringToLocalTime("15:10"), convertStringToLocalTime("16:00"), "Spaziergang", "Spaziergang im Park, Patient döst  im Rollstuhl ein"));
            dao.create(new Treatment(8, 1, convertStringToLocalDate("2023-06-08"), convertStringToLocalTime("15:00"), convertStringToLocalTime("16:00"), "Spaziergang", "Parkspaziergang; Patient ist heute lebhafter und hat klare Momente; erzählt von seiner Tochter"));
            dao.create(new Treatment(9, 2, convertStringToLocalDate("2023-06-07"), convertStringToLocalTime("11:00"), convertStringToLocalTime("11:30"), "Waschen", "Waschen per Dusche auf einem Stuhl; Patientin gewendet;"));
            dao.create(new Treatment(12, 5, convertStringToLocalDate("2023-06-08"), convertStringToLocalTime("15:00"), convertStringToLocalTime("15:30"), "Physiotherapie", "Übungen zur Stabilisation und Mobilisierung der Rückenmuskulatur"));
            dao.create(new Treatment(14, 4, convertStringToLocalDate("2023-08-24"), convertStringToLocalTime("09:30"), convertStringToLocalTime("10:15"), "KG", "Lympfdrainage"));
            dao.create(new Treatment(16, 6, convertStringToLocalDate("2023-08-31"), convertStringToLocalTime("13:30"), convertStringToLocalTime("13:45"), "Toilettengang", "Hilfe beim Toilettengang; Patientin klagt über Schmerzen beim Stuhlgang. Gabe von Iberogast"));
            dao.create(new Treatment(17, 6, convertStringToLocalDate("2023-09-01"), convertStringToLocalTime("16:00"), convertStringToLocalTime("17:00"), "KG", "Massage der Extremitäten zur Verbesserung der Durchblutung"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Die main Methode wird ausgeführt wenn diese Java Datei ausgeführt wird unf ruft die setIpDb Methode auf.
     **/
    public static void main(String[] args) {
        SetUpDB.setUpDb();
    }
}
