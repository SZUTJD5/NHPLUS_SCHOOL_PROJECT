package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.controller.PasswordHashingController;
import de.hitec.nhplus.model.Caregiver;
import de.hitec.nhplus.utils.DateConverter;
import javafx.scene.control.TextField;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implementiert die Schnittstelle <code>DaoImp</code>. Überschreibt Methoden, um bestimmte <code>PreparedStatements</code> zu generieren,
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
     * @return <code>   PreparedStatement</code> zum Einfügen des angegebenen Angestellten.
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
            checkAndDeleteUnlinkedLockedCaregivers();
            // Select all caregivers who are not locked
            final String SQL = "SELECT * FROM caregiver WHERE locked IS false AND cid >= 1";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
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

    /**
     * Aktualisiert den Sperrstatus eines Pflegepersonals anhand seiner ID (cid).
     * Löscht den Patienten, wenn keine zugeordneten Behandlungen vorhanden sind oder wenn die neueste Behandlung älter als zehn Jahre ist.
     *
     * @param cid    ID des Pflegepersonals, dessen Sperrstatus aktualisiert werden soll.
     * @param locked Neuer Sperrstatus.
     * @throws SQLException bei SQL-Fehlern.
     */
    public void updateLockStatus(long cid, boolean locked) throws SQLException {
        final String SQL = "UPDATE caregiver SET locked = ? WHERE cid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setBoolean(1, locked);
            preparedStatement.setLong(2, cid);
            preparedStatement.executeUpdate();
        }
        if (hasNoLinkedTreatments(cid) && locked) {
            if (deleteByCid(cid)) {
                System.out.println("Caregiver was deleted since there were no treatments linked to them\n");
            } else {
                System.out.println("Error autodelete 1\n");
            }
        } else if (isNewestTreatmentOlderThanTenYears(cid) && locked) {
            if (deleteByCid(cid)) {
                System.out.println("Caregiver was deleted since there were no treatments linked to them within the last 10 years\n");

            } else {
                System.out.println("Error autodelete 2\n");
            }
        } else {
            System.out.println("Caregiver was locked\n");
        }
    }

    /**
     * Überprüft und löscht (automatisch) gesperrte Pflegekräfte, die keine verknüpften Behandlungen haben oder deren neueste Behandlung älter als zehn Jahre ist.
     *
     * @throws SQLException bei SQL-Fehlern.
     */
    private void checkAndDeleteUnlinkedLockedCaregivers() throws SQLException {
        final String SQL = "SELECT * FROM caregiver WHERE locked IS true";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    boolean isLocked = resultSet.getBoolean("locked");
                    long cid = resultSet.getLong("cid");
                    if (isNewestTreatmentOlderThanTenYears(cid) && isLocked) {
                        if (deleteByCid(cid)) {
                            System.out.println("(AUTO) Deleted locked caregiver cause no treatments were newer than 10 years\n");
                        } else {
                            System.out.println("Error autodelete 4\n");
                        }
                    } else if (hasNoLinkedTreatments(cid) && isLocked) {
                        if (deleteByCid(cid)) {
                            System.out.println("(AUTO) deleted locked caregiver since there were no treatments linked to them\n");
                        } else {
                            System.out.println("Error autodelete 3\n");
                        }
                    } else {
                        System.out.println("Found no reason to delete");
                    }
                }
            }
        }
    }

    /**
     * Überprüft, ob die neueste Behandlung einer Pflegekraft älter als zehn Jahre ist.
     *
     * @param cid ID des Pflegepersonals.
     * @return <code>true</code>, wenn die neueste Behandlung älter als zehn Jahre ist, andernfalls <code>false</code>.
     * @throws SQLException bei SQL-Fehlern.
     */
    private boolean isNewestTreatmentOlderThanTenYears(long cid) throws SQLException {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the date 10 years ago
        LocalDate tenYearsAgo = currentDate.minusYears(10);

        // Convert LocalDate to String
        String tenYearsAgoString = DateConverter.convertLocalDateToString(tenYearsAgo);

        // SQL query to select the amount of treatments that are newer than 10 years
        String sql = "SELECT COUNT(*) FROM treatment WHERE treatment_date > ? AND cid = ?";

        // Prepare the statement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, tenYearsAgoString);
        preparedStatement.setLong(2, cid);

        // Execute the query and store in result set
        ResultSet resultSet = preparedStatement.executeQuery();

        // Get the result count
        int count = 0;
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        }

        return count == 0;
    }

    /**
     * Löscht einen Pflegekraft und verknüpfte Einträge anhand der Pflegekraft-ID (cid).
     *
     * @param cid ID der zu löschenden Pflegekraft.
     * @return <code>true</code>, wenn die Pflegekraft erfolgreich gelöscht wurde, andernfalls <code>false</code>.
     */
    private boolean deleteByCid(long cid) {
        try {
            // Replaces the cid of the caregiver in treatment with -1
            final String updateSQL = "UPDATE treatment SET cid = 0 WHERE cid = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateSQL);
            updateStatement.setLong(1, cid);
            updateStatement.executeUpdate();

            // Deletes the Caregiver Login
            final String deleteLoginSQL = "DELETE FROM logins WHERE name = ?";
            PreparedStatement deleteLoginStatement = connection.prepareStatement(deleteLoginSQL);
            deleteLoginStatement.setString(1, retrieveCaregiverByCid(cid).getLoginName());
            deleteLoginStatement.executeUpdate();

            // Deletes the caregiver
            final String deleteSQL = "DELETE FROM caregiver WHERE cid = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL);
            deleteStatement.setLong(1, cid);
            deleteStatement.executeUpdate();
            return true;

        } catch (SQLException exception) {
            System.out.println("Error deleting caregiver: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Überprüft, ob eine Pflegekraft keine verknüpften Behandlungen hat.
     *
     * @param cid ID der Pflegekraft.
     * @return <code>true</code>, wenn keine verknüpften Behandlungen vorhanden sind, andernfalls <code>false</code>.
     * @throws SQLException bei SQL-Fehlern.
     */
    private boolean hasNoLinkedTreatments(long cid) throws SQLException {
        final String SQL = "SELECT COUNT(*) FROM treatment WHERE cid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setLong(1, cid);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    if (resultSet.getInt(1) <= 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Ruft einen Pflegekraftdatensatz anhand der Pflegekraft-ID (cid) ab.
     *
     * @param cid ID der Pflegekraft.
     * @return <code>Caregiver</code>-Objekt mit den Daten der Pflegekraft.
     * @throws SQLException bei SQL-Fehlern.
     */
    public Caregiver retrieveCaregiverByCid(long cid) throws SQLException {
        Caregiver caregiver = null;
        final String SQL = "SELECT * FROM caregiver WHERE cid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setLong(1, cid);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    caregiver = new Caregiver(resultSet.getLong("cid"), resultSet.getString("firstname"), resultSet.getString("surname"), resultSet.getString("phoneNumber"), resultSet.getBoolean("locked"));
                }
            }
        }
        return caregiver;
    }

    /**
     * Fügt einer Pflegekraft einen neuen Login hinzu.
     *
     * @param caregiver         Pflegekraft, der der Login hinzugefügt wird.
     * @param textFieldPassword Passwortfeld mit dem neuen Passwort.
     */
    public void addLogin(Caregiver caregiver, TextField textFieldPassword) {
        String loginName = caregiver.getFirstName() + "," + caregiver.getSurname();
        String password = textFieldPassword.getText();
        PasswordHashingController passwordHashingController = new PasswordHashingController();
        password = passwordHashingController.hashPassword(password);

        final String SQL = "INSERT INTO logins (name, password) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, loginName);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("Error adding login: " + exception.getMessage());
        }
    }
}