// Paketanweisung und Importe

package de.hitec.nhplus.model;

import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.utils.DateConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

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
     * um Objekte zu initiieren, die noch nicht persistent sind, da sie keine Behandlungs-ID (tid) haben.
     *
     * @param pid         Id des behandelten Patienten.
     * @param date        Datum der Behandlung.
     * @param begin       Uhrzeit des Beginns der Behandlung im Format „hh:MM“
     * @param end         Uhrzeit des Behandlungsendes im Format „hh:MM“.
     * @param description Beschreibung der Behandlung.
     * @param remarks     Bemerkungen zur Behandlung.
     * @param locked      Locked Status des Patienten
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
     * um Objekte zu initiieren, die bereits persistiert sind und eine Behandlungs-ID (tid) haben.
     *
     * @param tid         ID der Behandlung.
     * @param pid         Id des behandelten Patienten.
     * @param date        Datum der Behandlung.
     * @param begin       Uhrzeit des Beginns der Behandlung im Format „hh:MM“
     * @param end         Uhrzeit des Behandlungsendes im Format „hh:MM“.
     * @param description Beschreibung der Behandlung.
     * @param remarks     Bemerkungen zur Behandlung.
     * @param locked      Locked Status des Patienten
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

    // Getter für Behandlungs-ID
    public long getTid() {
        return tid;
    }

    // Getter für Patienten-ID
    public long getPid() {
        return this.pid;
    }

    // Getter für Datum der Behandlung
    public String getDate() {
        return date.toString();
    }

    // Setter für Datum der Behandlung
    public void setDate(String date) {
        this.date = DateConverter.convertStringToLocalDate(date);
    }

    // Getter für Startzeit der Behandlung
    public String getBegin() {
        return begin.toString();
    }

    // Setter für Startzeit der Behandlung
    public void setBegin(String begin) {
        this.begin = DateConverter.convertStringToLocalTime(begin);
    }

    // Getter für Endzeit der Behandlung
    public String getEnd() {
        return end.toString();
    }

    // Setter für Endzeit der Behandlung
    public void setEnd(String end) {
        this.end = DateConverter.convertStringToLocalTime(end);
    }

    // Getter für Beschreibung der Behandlung
    public String getDescription() {
        return description;
    }

    // Setter für Beschreibung der Behandlung
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter für Anmerkungen zur Behandlung
    public String getRemarks() {
        return remarks;
    }

    // Setter für Anmerkungen zur Behandlung
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean getLocked() {
        return locked;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    // Method to retrieve caregiver by cid
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