package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Caregiver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementiert die Schnittstelle <code>DaoImp</code>. Überschreibt Methoden, um bestimmte <code>PreparedStatements</code> zu generieren.
 * um die spezifischen SQL-Anweisungen auszuführen.
 */
public class CaregiverDao extends DaoImp<Caregiver> {

    /**
     * Der Konstruktor initiiert ein Objekt von <code>CaregiverDao</code> und übergibt die Verbindung an seine Superklasse.
     *
     * @param connection Objekt von <code>Connection</code> zur Ausführung der SQL-Anweisungen.
     */
    public CaregiverDao(Connection connection) {
        super(connection);
    }

    /**
     * Erzeugt ein <code>PreparedStatement</code>, um das angegebene Objekt von <code>Caregiver</code> beizubehalten.
     *
     * @param caregiver Objekt von <code>Caregiver</code>, das bestehen bleiben soll.
     * @return <code>PreparedStatement</code> zum Einfügen des angegebenen Angestellten.
     */
    @Override
    protected PreparedStatement getCreateStatement(Caregiver caregiver) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "INSERT INTO caregiver (firstname, surname, phoneNumber, locked) " + "VALUES (?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, caregiver.getFirstName());
            preparedStatement.setString(2, caregiver.getSurname());
            preparedStatement.setString(3, caregiver.getPhoneNumber());
            preparedStatement.setBoolean(4, caregiver.getLocked());
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
        return preparedStatement;
    }

    /**
     * Erzeugt ein <code>PreparedStatement</code>, um einen Angestellten anhand einer bestimmten Angestellten-ID (PID) abzufragen.
     *
     * @param pid Angestellten-ID zur Abfrage.
     * @return <code>PreparedStatement</code> zur Abfrage des Angestellten.
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long pid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM caregiver WHERE cid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, pid);
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
        return preparedStatement;
    }

    /**
     * Ordnet ein <code>ResultSet</code> eines Angestellten einem Objekt von <code>Caregiver</code> zu.
     *
     * @param result ResultSet mit einer einzelnen Zeile. Spalten werden einem Objekt der Klasse <code>Caregiver</code> zugeordnet.
     * @return Objekt der Klasse <code>Caregiver</code> mit den Daten aus dem resultSet.
     */
    @Override
    protected Caregiver getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new Caregiver(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getBoolean(5));
    }

    /**
     * Erzeugt ein <code>PreparedStatement</code> zur Abfrage aller Angestellten.
     *
     * @return <code>PreparedStatement</code> zur Abfrage aller Angestellten.
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            final String SQL = "SELECT * FROM caregiver WHERE locked IS false";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
        return statement;
    }

    /**
     * Ordnet ein <code>ResultSet</code> aller Angestellten einer <code>ArrayList</code> von <code>Caregiver</code>-Objekten zu.
     *
     * @param result ResultSet mit allen Zeilen. Die Spalten werden Objekten der Klasse <code>Caregiver</code> zugeordnet.
     * @return <code>ArrayList</code> mit Objekten der Klasse <code>Caregiver</code> aller Zeilen im
     * <code>ResultSet</code>.
     */
    @Override
    protected ArrayList<Caregiver> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Caregiver> list = new ArrayList<>();
        while (result.next()) {
            Caregiver caregiver = new Caregiver(result.getLong(1), result.getString(2), result.getString(3), result.getString(4), result.getBoolean(5));
            list.add(caregiver);
        }
        return list;
    }

    /**
     * Erzeugt ein <code>PreparedStatement</code>, um den angegebenen identifizierten Angestellten zu aktualisieren
     * nach der ID des Angestellten (pid).
     *
     * @param caregiver Angestelltenobjekt, das aktualisiert werden soll.
     * @return <code>PreparedStatement</code>, um den angegebenen Angestellten zu aktualisieren.
     */
    @Override
    protected PreparedStatement getUpdateStatement(Caregiver caregiver) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "UPDATE caregiver SET " + "firstname = ?, " + "surname = ?, " + "phoneNumber = ?, " + "locked = ? " + "WHERE cid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, caregiver.getFirstName());
            preparedStatement.setString(2, caregiver.getSurname());
            preparedStatement.setString(3, caregiver.getPhoneNumber());
            preparedStatement.setBoolean(4, caregiver.getLocked());
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
        return preparedStatement;
    }

    // Setzt locked auf true
    public void updateLockStatus(long cid, boolean locked) throws SQLException {
        final String SQL = "UPDATE caregiver SET locked = ? WHERE cid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setBoolean(1, locked); // Wechselt locked auf "1"
            preparedStatement.setLong(2, cid);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Erzeugt ein <code>PreparedStatement</code>, um einen Angestellten mit der angegebenen ID zu löschen.
     *
     * @param cid-ID des zu löschenden Angestellten.
     * @return <code>PreparedStatement</code>, um den Angestellten mit der angegebenen ID zu löschen.
     */

    @Override
    protected PreparedStatement getDeleteStatement(long cid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "DELETE FROM caregiver WHERE cid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, cid);
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
        return preparedStatement;
    }
}
