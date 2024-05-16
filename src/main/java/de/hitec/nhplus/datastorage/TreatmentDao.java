package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.utils.DateConverter;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementiert die Schnittstelle <code>DaoImp</code>. Überschreibt Methoden, um bestimmte <code>PreparedStatements</code> zu generieren.
 * um die spezifischen SQL-Anweisungen auszuführen.
 */
public class TreatmentDao extends DaoImp<Treatment> {

    /**
     * Der Konstruktor initiiert ein Objekt von <code>TreatmentDao</code> und übergibt die Verbindung an seine Superklasse.
     *
     * @param connection Objekt von <code>Connection</code> zur Ausführung der SQL-Anweisungen.
     */
    public TreatmentDao(Connection connection) {
        super(connection);
    }

    /**
     * Erzeugt ein <code>PreparedStatement</code>, um das angegebene Objekt von <code>Treatment</code> beizubehalten.
     *
     * @param treatment Objekt von <code>Treatment</code>, das bestehen bleiben soll.
     * @return <code>PreparedStatement</code> zum Einfügen des angegebenen Patienten.
     */
    @Override
    protected PreparedStatement getCreateStatement(Treatment treatment) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "INSERT INTO treatment (pid, treatment_date, begin, end, description, remark, locked, cid) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, treatment.getPid());
            preparedStatement.setString(2, treatment.getDate());
            preparedStatement.setString(3, treatment.getBegin());
            preparedStatement.setString(4, treatment.getEnd());
            preparedStatement.setString(5, treatment.getDescription());
            preparedStatement.setString(6, treatment.getRemarks());
            preparedStatement.setBoolean(7, treatment.getLocked());
            preparedStatement.setLong(8, treatment.getCid());
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
        return preparedStatement;
    }


    @Override
    protected PreparedStatement getReadByIDStatement(long key) {
        return null;
    }

    /**
     * Ordnet ein <code>ResultSet</code> einer Behandlung einem Objekt von <code>Treatment</code> zu.
     *
     * @param result ResultSet mit einer einzelnen Zeile. Spalten werden einem Objekt der Klasse <code>Treatment</code> zugeordnet.
     * @return Objekt der Klasse <code>Treatment</code> mit den Daten aus dem resultSet.
     */
    @Override
    protected Treatment getInstanceFromResultSet(ResultSet result) throws SQLException {
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
        LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
        LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
        return new Treatment(result.getLong(1), result.getLong(2), date, begin, end, result.getString(6), result.getString(7), result.getBoolean(8), result.getLong(9));
    }

    @Override
    protected ArrayList<Treatment> getListFromResultSet(ResultSet set) throws SQLException {
        ArrayList<Treatment> treatments = new ArrayList<>();
        while (set.next()) {
            treatments.add(getInstanceFromResultSet(set));
        }
        return treatments;
    }


    /**
     * Erzeugt ein <code>PreparedStatement</code> zur Abfrage aller Behandlungen.
     *
     * @return <code>PreparedStatement</code> zur Abfrage aller Behandlungen.
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            deleteOldLockedTreatments();
            final String SQL = "SELECT * FROM treatment WHERE locked IS false";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
        return statement;
    }

    /**
     * Erzeugt ein <code>PreparedStatement</code>, um alle Behandlungen eines Patienten mit einer bestimmten Patienten-ID (PID) abzufragen.
     *
     * @param pid Patienten-ID zur Abfrage aller Behandlungen, die auf diese ID verweisen.
     * @return <code>PreparedStatement</code>, um alle Behandlungen der angegebenen Patienten-ID (PID) abzufragen.
     */
    private PreparedStatement getReadAllTreatmentsOfOnePatientByPid(long pid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM treatment WHERE pid = ? AND locked IS false";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, pid);
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
        return preparedStatement;
    }

    /**
     * Fragt alle Behandlungen einer bestimmten Patienten-ID (PID) ab und ordnet die Ergebnisse einer <code>ArrayList</code> zu
     * Objekte der Klasse <code>Treatment</code>.
     *
     * @param pid Patienten-ID zur Abfrage aller Behandlungen, die auf diese ID verweisen.
     * @return <code>ArrayList</code> mit Objekten der Klasse <code>Treatment</code> aller Zeilen im
     * <code>ResultSet</code>.
     */
    public List<Treatment> readTreatmentsByPid(long pid) throws SQLException {
        ResultSet result = getReadAllTreatmentsOfOnePatientByPid(pid).executeQuery();
        return getListFromResultSet(result);
    }

    /**
     * Erzeugt ein <code>PreparedStatement</code>, um die angegebene identifizierte Behandlung zu aktualisieren
     * nach der ID der Behandlung (tid).
     *
     * @param treatment Behandlungsobjekt, das aktualisiert werden soll.
     * @return <code>PreparedStatement</code>, um die angegebene Behandlung zu aktualisieren.
     */
    @Override
    protected PreparedStatement getUpdateStatement(Treatment treatment) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "UPDATE treatment SET " + "pid = ?, " + "treatment_date = ?, " + "begin = ?, " + "end = ?, " + "description = ?, " + "remark = ?, " + "locked = ? " + "WHERE tid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, treatment.getPid());
            preparedStatement.setString(2, treatment.getDate());
            preparedStatement.setString(3, treatment.getBegin());
            preparedStatement.setString(4, treatment.getEnd());
            preparedStatement.setString(5, treatment.getDescription());
            preparedStatement.setString(6, treatment.getRemarks());
            preparedStatement.setString(7, String.valueOf(treatment.getLocked()));
            preparedStatement.setLong(8, treatment.getTid());
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getDeleteStatement(long key) {
        return null;
    }

    /**
     * Setzt den Wert für "locked" für alle Behandlungen eines Patienten.
     *
     * @param pid  Patienten-ID.
     * @param lock Wert für "locked".
     */
    public void lockAllPatientTreatments(long pid, boolean lock) {
        try {
            final String SQL = "UPDATE treatment SET locked = ? WHERE pid = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setBoolean(1, lock);
            preparedStatement.setLong(2, pid);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
    }

    public void deleteOldLockedTreatments() {
        try {
            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Calculate the date 10 years ago
            LocalDate tenYearsAgo = currentDate.minusYears(10);

            // Convert LocalDate to String
            String tenYearsAgoString = DateConverter.convertLocalDateToString(tenYearsAgo);

            // SQL query to delete treatments older than 10 years and locked
            String sql = "DELETE FROM treatment WHERE locked = true AND treatment_date < ?";

            // Prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tenYearsAgoString);

            // Execute the delete statement
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("Error deleting old locked treatments: " + exception.getMessage());
        }
    }
}
