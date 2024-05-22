package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.utils.DateConverter;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implementiert die Schnittstelle <code>DaoImp</code>. Überschreibt Methoden, um bestimmte <code>PreparedStatements</code> zu generieren.
 * um die spezifischen SQL-Anweisungen auszuführen.
 */
public class PatientDao extends DaoImp<Patient> {

    /**
     * Der Konstruktor initiiert ein Objekt von <code>PatientDao</code> und übergibt die Verbindung an seine Superklasse.
     *
     * @param connection Objekt von <code>Connection</code> zur Ausführung der SQL-Anweisungen.
     */
    public PatientDao(Connection connection) {
        super(connection);
    }

    /**
     * Erzeugt ein <code>PreparedStatement</code>, um das angegebene Objekt von <code>Patient</code> beizubehalten.
     *
     * @param patient Objekt von <code>Patient</code>, das bestehen bleiben soll.
     * @return <code>PreparedStatement</code> zum Einfügen des angegebenen Patienten.
     */
    @Override
    protected PreparedStatement getCreateStatement(Patient patient) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "INSERT INTO patient (firstname, surname, dateOfBirth, carelevel, roomnumber, locked, arrival_date) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getSurname());
            preparedStatement.setString(3, patient.getDateOfBirth());
            preparedStatement.setString(4, patient.getCareLevel());
            preparedStatement.setString(5, patient.getRoomNumber());
            preparedStatement.setString(6, patient.getLocked() ? "1" : "0"); // Locked Standartwert = 0 (false)
            preparedStatement.setString(7, DateConverter.convertLocalDateToString(LocalDate.now()));
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
        return preparedStatement;
    }

    /**
     * Erzeugt ein <code>PreparedStatement</code>, um einen Patienten anhand einer bestimmten Patienten-ID (PID) abzufragen.
     *
     * @param pid Patienten-ID zur Abfrage.
     * @return <code>PreparedStatement</code> zur Abfrage des Patienten.
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long pid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM patient WHERE pid = ? AND locked = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, pid);
            preparedStatement.setBoolean(2, false); // Nur Patienten anzeigen die nicht gesperrt sind.
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
        return preparedStatement;
    }

    /**
     * Ordnet ein <code>ResultSet</code> eines Patienten einem Objekt von <code>Patient</code> zu.
     *
     * @param result ResultSet mit einer einzelnen Zeile. Spalten werden einem Objekt der Klasse <code>Patient</code> zugeordnet.
     * @return Objekt der Klasse <code>Patient</code> mit den Daten aus dem resultSet.
     */
    @Override
    protected Patient getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new Patient(result.getInt(1), result.getString(2), result.getString(3), DateConverter.convertStringToLocalDate(result.getString(4)), result.getString(5), result.getString(6), result.getBoolean(7));
    }

    /**
     * Erzeugt ein <code>PreparedStatement</code> zur Abfrage aller Patienten.
     *
     * @return <code>PreparedStatement</code> zur Abfrage aller Patienten.
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            deleteOldLockedPatients();
            // Nur Patienten anzeigen die nicht gesperrt sind mit einer PID über 0
            // (0 ist der placeholder für gelöschte patienten)
            final String SQL = "SELECT * FROM patient WHERE locked = ? AND pid > 0";
            statement = this.connection.prepareStatement(SQL);
            statement.setBoolean(1, false);
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
        return statement;
    }

    public void deleteOldLockedPatients() {
        try {
            // Datum von heute
            LocalDate currentDate = LocalDate.now();

            // Datum heute vor 10 Jahren Berechnen
            LocalDate tenYearsAgo = currentDate.minusYears(10);

            // LocalDate zu String
            String tenYearsAgoString = DateConverter.convertLocalDateToString(tenYearsAgo);

            // SQL Statement um alle gesperrten Patienten anzuzeigen die vor mehr als 10 Jahren erstellt wurden.
            String selectSQL = "SELECT pid FROM patient WHERE locked = true AND arrival_date < ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, tenYearsAgoString);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Code wird für alle gesperrten Patienten ausgeführt welche vor mehr als 10 Jahren eingetragen wurden.
            while (resultSet.next()) {
                long tempPid = resultSet.getLong("pid");

                // Pid der mit dem Patienten verlinkten Behandlungen wird auf 0 gesetzt.
                String updateSQL = "UPDATE treatment SET pid = 0 WHERE pid = ?";
                PreparedStatement updateStatement = this.connection.prepareStatement(updateSQL);
                updateStatement.setLong(1, tempPid);
                updateStatement.executeUpdate();

                // Patient aus Datenbank löschen
                String deleteSQL = "DELETE FROM patient WHERE pid = ?";
                PreparedStatement deleteStatement = this.connection.prepareStatement(deleteSQL);
                deleteStatement.setLong(1, tempPid);
                deleteStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            System.out.println("Error deleting old locked patients and updating treatments: " + exception.getMessage());
        }
    }


    /**
     * Ordnet ein <code>ResultSet</code> aller Patienten einer <code>ArrayList</code> von <code>Patient</code>-Objekten zu.
     *
     * @param result ResultSet mit allen Zeilen. Die Spalten werden Objekten der Klasse <code>Patient</code> zugeordnet.
     * @return <code>ArrayList</code> mit Objekten der Klasse <code>Patient</code> aller Zeilen im
     * <code>ResultSet</code>.
     */
    @Override
    protected ArrayList<Patient> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Patient> list = new ArrayList<>();
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
            Patient patient = new Patient(result.getInt(1), result.getString(2), result.getString(3), date, result.getString(5), result.getString(6), result.getBoolean(7));
            list.add(patient);
        }
        return list;
    }

    /**
     * Erzeugt ein <code>PreparedStatement</code>, um den angegebenen identifizierten Patienten zu aktualisieren
     * nach der ID des Patienten (pid).
     *
     * @param patient Patientenobjekt, das aktualisiert werden soll.
     * @return <code>PreparedStatement</code>, um den angegebenen Patienten zu aktualisieren.
     */
    @Override
    protected PreparedStatement getUpdateStatement(Patient patient) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "UPDATE patient SET " + "firstname = ?, " + "surname = ?, " + "dateOfBirth = ?, " + "carelevel = ?, " + "roomnumber = ? " + "WHERE pid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getSurname());
            preparedStatement.setString(3, patient.getDateOfBirth());
            preparedStatement.setString(4, patient.getCareLevel());
            preparedStatement.setString(5, patient.getRoomNumber());
            preparedStatement.setLong(6, patient.getPid());
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
        return preparedStatement;
    }

    /**
     * Aktualisiert den Sperrstatus eines Patienten.
     *
     * @param pid    Die ID des Patienten.
     * @param locked true, wenn der Patient gesperrt ist, andernfalls false.
     * @throws SQLException, wenn ein Datenbankzugriffsfehler auftritt.
     */
    public void updateLockStatus(long pid, boolean locked) throws SQLException {
        final String SQL = "UPDATE patient SET locked = ? WHERE pid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setBoolean(1, locked); // Wechselt locked auf "1"
            preparedStatement.setLong(2, pid);
            preparedStatement.executeUpdate();
        }
    }


    /**
     * Erzeugt ein <code>PreparedStatement</code>, um einen Patienten mit der angegebenen ID zu löschen.
     *
     * @param pid-ID des zu löschenden Patienten.
     * @return <code>PreparedStatement</code>, um den Patienten mit der angegebenen ID zu löschen.
     */
    @Override
    protected PreparedStatement getDeleteStatement(long pid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "DELETE FROM patient WHERE pid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, pid);
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
        return preparedStatement;
    }
}
