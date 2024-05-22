// Paketanweisung und Importe

package de.hitec.nhplus.model;

import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.utils.DateConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Klasse Treatment, stellt Logik für Behandlungen bereit.
 */
public class Treatment {
    private long tid;
    private long pid;
    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;
    private String description;
    private String remarks;
    private boolean locked;
    private long cid;

    /**
     * Konstruktor zum Initiieren eines Objekts der Klasse <code>Treatment</code> mit dem angegebenen Parameter. Verwenden Sie diesen Konstruktor
     * um Objekte zu initiieren, die noch nicht persistent sind, da sie keine Behandlung-ID (tid) haben.
     *
     * @param pid         Id des behandelten Patienten.
     * @param date        Datum der Behandlung.
     * @param begin       Uhrzeit des Beginns der Behandlung im Format „hh:MM“
     * @param end         Uhrzeit des Behandlungsendes im Format „hh:MM“.
     * @param description Beschreibung der Behandlung.
     * @param remarks     Bemerkungen zur Behandlung.
     * @param locked      Locked Status des Patienten
     * @param cid         ID des Behandelnden Pflegers
     */
    public Treatment(long pid, LocalDate date, LocalTime begin, LocalTime end, String description, String remarks, boolean locked, long cid) {
        this.pid = pid;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
        this.locked = locked;
        this.cid = cid;
    }


    /**
     * Konstruktor zum Initiieren eines Objekts der Klasse <code>Treatment</code> mit dem angegebenen Parameter. Verwenden Sie diesen Konstruktor
     * um Objekte zu initiieren, die bereits persistiert sind und eine Behandlung-ID (tid) haben.
     *
     * @param tid         ID der Behandlung.
     * @param pid         Id des behandelten Patienten.
     * @param date        Datum der Behandlung.
     * @param begin       Uhrzeit des Beginns der Behandlung im Format „hh:MM“
     * @param end         Uhrzeit des Behandlungsendes im Format „hh:MM“.
     * @param description Beschreibung der Behandlung.
     * @param remarks     Bemerkungen zur Behandlung.
     * @param locked      Locked Status des Patienten
     * @param cid         ID des behandelden Pflegers
     */
    public Treatment(long tid, long pid, LocalDate date, LocalTime begin, LocalTime end, String description, String remarks, boolean locked, long cid) {
        this.tid = tid;
        this.pid = pid;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
        this.locked = locked;
        this.cid = cid;
    }

    /**
     * Getter für Behandlung-ID
     *
     * @return tid
     */
    public long getTid() {
        return tid;
    }

    /**
     * Getter für Patienten-ID
     *
     * @return pid
     */
    public long getPid() {
        return this.pid;
    }

    /**
     * Getter für Datum der Behandlung
     *
     * @return String date
     */
    public String getDate() {
        return date.toString();
    }

    /**
     * Setter für Datum der Behandlung
     *
     * @param date Datum der Behandlung
     */
    public void setDate(String date) {
        this.date = DateConverter.convertStringToLocalDate(date);
    }

    /**
     * Getter für Startzeit der Behandlung
     *
     * @return String der Anfangszeit
     */
    public String getBegin() {
        return begin.toString();
    }

    /**
     * Setter für Startzeit der Behandlung
     *
     * @param begin Startzeitpunkt der Behandlung
     */
    public void setBegin(String begin) {
        this.begin = DateConverter.convertStringToLocalTime(begin);
    }

    /**
     * Getter für Endzeit der Behandlung
     *
     * @return String der Endzeit
     */
    public String getEnd() {
        return end.toString();
    }

    /**
     * Setter für Endzeit der Behandlung
     *
     * @param end Endzeit der Behandlung
     */
    public void setEnd(String end) {
        this.end = DateConverter.convertStringToLocalTime(end);
    }

    /**
     * Getter für Beschreibung der Behandlung
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter für Beschreibung der Behandlung
     *
     * @param description Beschreibung der Behandlung
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter für Anmerkungen zur Behandlung
     *
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Setter für Anmerkungen zur Behandlung
     *
     * @param remarks Detaillierte Beschreibung der Behandlung
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * Getter zum locked Status der Behandlung
     *
     * @return locked
     */
    public boolean getLocked() {
        return locked;
    }

    /**
     * Gibt die CaregiverID aus
     *
     * @return cid
     */
    public long getCid() {
        return cid;
    }

    /**
     * Setter um Cid fest zu legen
     *
     * @param cid CaregiverID des Caregivers
     */
    public void setCid(long cid) {
        this.cid = cid;
    }

    /**
     * Method to retrieve caregiver by cid
     *
     * @param cid CaregiverID des zu findenden caregivers
     * @return Caregiver
     */
    public Caregiver retrieveCaregiver(long cid) {
        // Implement your logic here to retrieve the caregiver by cid
        // You will have to use your data access layer to get the caregiver from the database
        try {
            return DaoFactory.getDaoFactory().createCaregiverDAO().retrieveCaregiverByCid(cid);
        } catch (SQLException exception) {
            return null;
        }
    }

    // Überschriebene toString-Methode zur Darstellung der Behandlungsinformationen
    public String toString() {
        return "\nBehandlung" + "\nTID: " + this.tid + "\nPID: " + this.pid + "\nDate: " + this.date + "\nBegin: " + this.begin + "\nEnd: " + this.end + "\nDescription: " + this.description + "\nRemarks: " + this.remarks + "\n";
    }
}