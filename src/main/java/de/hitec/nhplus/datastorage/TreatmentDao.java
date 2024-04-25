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
            final String SQL = "INSERT INTO treatment (pid, treatment_date, begin, end, description, remark) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, treatment.getPid());
            preparedStatement.setString(2, treatment.getDate());
            preparedStatement.setString(3, treatment.getBegin());
            preparedStatement.setString(4, treatment.getEnd());
            preparedStatement.setString(5, treatment.getDescription());
            preparedStatement.setString(6, treatment.getRemarks());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Erzeugt ein <code>PreparedStatement</code>, um eine Behandlung anhand einer bestimmten Behandlungs-ID (tid) abzufragen.
     *
     * @param tid Behandlungs-ID zur Abfrage.
     * @return <code>PreparedStatement</code> zur Abfrage der Behandlung.
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long tid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM treatment WHERE tid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, tid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
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
        return new Treatment(result.getLong(1), result.getLong(2),
                date, begin, end, result.getString(6), result.getString(7));
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
            final String SQL = "SELECT * FROM treatment";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return statement;
    }

    /**
     * Ordnet ein <code>ResultSet</code> aller Behandlungen einer <code>ArrayList</code> mit Objekten der Klasse zu
     * <code>Behandlung</code>.
     *
     * @param result ResultSet mit allen Zeilen. Die Spalten werden Objekten der Klasse <code>Treatment</code> zugeordnet.
     * @return <code>ArrayList</code> mit Objekten der Klasse <code>Treatment</code> aller Zeilen im
     * <code>ResultSet</code>.
     */
    @Override
    protected ArrayList<Treatment> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Treatment> list = new ArrayList<Treatment>();
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
            LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
            LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
            Treatment treatment = new Treatment(result.getLong(1), result.getLong(2),
                    date, begin, end, result.getString(6), result.getString(7));
            list.add(treatment);
        }
        return list;
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
            final String SQL = "SELECT * FROM treatment WHERE pid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, pid);
        } catch (SQLException exception) {
            exception.printStackTrace();
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
            final String SQL =
                    "UPDATE treatment SET " +
                            "pid = ?, " +
                            "treatment_date = ?, " +
                            "begin = ?, " +
                            "end = ?, " +
                            "description = ?, " +
                            "remark = ? " +
                            "WHERE tid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, treatment.getPid());
            preparedStatement.setString(2, treatment.getDate());
            preparedStatement.setString(3, treatment.getBegin());
            preparedStatement.setString(4, treatment.getEnd());
            preparedStatement.setString(5, treatment.getDescription());
            preparedStatement.setString(6, treatment.getRemarks());
            preparedStatement.setLong(7, treatment.getTid());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Erzeugt ein <code>PreparedStatement</code> zum Löschen einer Behandlung mit der angegebenen ID.
     *
     * @param tid ID der zu löschenden Behandlung.
     * @return <code>PreparedStatement</code>, um die Behandlung mit der angegebenen ID zu löschen.
     */
    @Override
    protected PreparedStatement getDeleteStatement(long tid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL =
                    "DELETE FROM treatment WHERE tid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, tid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }
}