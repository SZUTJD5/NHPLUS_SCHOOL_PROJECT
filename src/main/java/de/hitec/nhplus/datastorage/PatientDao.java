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
            final String SQL = "INSERT INTO patient (firstname, surname, dateOfBirth, carelevel, roomnumber) " +
                    "VALUES (?, ?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getSurname());
            preparedStatement.setString(3, patient.getDateOfBirth());
            preparedStatement.setString(4, patient.getCareLevel());
            preparedStatement.setString(5, patient.getRoomNumber());
        } catch (SQLException exception) {
            exception.printStackTrace();
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
            final String SQL = "SELECT * FROM patient WHERE pid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, pid);
        } catch (SQLException exception) {
            exception.printStackTrace();
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
        return new Patient(
                result.getInt(1),
                result.getString(2),
                result.getString(3),
                DateConverter.convertStringToLocalDate(result.getString(4)),
                result.getString(5),
                result.getString(6));
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
            final String SQL = "SELECT * FROM patient";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return statement;
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
            Patient patient = new Patient(result.getInt(1), result.getString(2),
                    result.getString(3), date,
                    result.getString(5), result.getString(6));
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
            final String SQL =
                    "UPDATE patient SET " +
                            "firstname = ?, " +
                            "surname = ?, " +
                            "dateOfBirth = ?, " +
                            "carelevel = ?, " +
                            "roomnumber = ? " +
                            "WHERE pid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getSurname());
            preparedStatement.setString(3, patient.getDateOfBirth());
            preparedStatement.setString(4, patient.getCareLevel());
            preparedStatement.setString(5, patient.getRoomNumber());
            preparedStatement.setLong(6, patient.getPid());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
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
            exception.printStackTrace();
        }
        return preparedStatement;
    }
}
