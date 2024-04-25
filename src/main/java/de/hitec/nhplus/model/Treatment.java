// Paketanweisung und Importe
package de.hitec.nhplus.model;

import de.hitec.nhplus.utils.DateConverter;

import java.time.LocalDate;
import java.time.LocalTime;

// Definition der Treatment-Klasse
public class Treatment {
    private long tid; // Behandlungs-ID
    private final long pid; // Finale Patienten-ID
    private LocalDate date; // Datum der Behandlung
    private LocalTime begin; // Startzeit der Behandlung
    private LocalTime end; // Endzeit der Behandlung
    private String description; // Beschreibung der Behandlung
    private String remarks; // Anmerkungen zur Behandlung

    /**
     * Constructor to initiate an object of class <code>Treatment</code> with the given parameter. Use this constructor
     * to initiate objects, which are not persisted yet, because it will not have a treatment id (tid).
     *
     * @param pid         Id of the treated patient.
     * @param date        Date of the Treatment.
     * @param begin       Time of the start of the treatment in format "hh:MM"
     * @param end         Time of the end of the treatment in format "hh:MM".
     * @param description Description of the treatment.
     * @param remarks     Remarks to the treatment.
     */
    public Treatment(long pid, LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks) {
        this.pid = pid;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
    }

    /**
     * Constructor to initiate an object of class <code>Treatment</code> with the given parameter. Use this constructor
     * to initiate objects, which are already persisted and have a treatment id (tid).
     *
     * @param tid         Id of the treatment.
     * @param pid         Id of the treated patient.
     * @param date        Date of the Treatment.
     * @param begin       Time of the start of the treatment in format "hh:MM"
     * @param end         Time of the end of the treatment in format "hh:MM".
     * @param description Description of the treatment.
     * @param remarks     Remarks to the treatment.
     */
    public Treatment(long tid, long pid, LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks) {
        this.tid = tid;
        this.pid = pid;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
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

    // Getter für Startzeit der Behandlung
    public String getBegin() {
        return begin.toString();
    }

    // Getter für Endzeit der Behandlung
    public String getEnd() {
        return end.toString();
    }

    // Setter für Datum der Behandlung
    public void setDate(String date) {
        this.date = DateConverter.convertStringToLocalDate(date);
    }

    // Setter für Startzeit der Behandlung
    public void setBegin(String begin) {
        this.begin = DateConverter.convertStringToLocalTime(begin);
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

    // Überschriebene toString-Methode zur Darstellung der Behandlungsinformationen
    public String toString() {
        return "\nBehandlung" + "\nTID: " + this.tid +
                "\nPID: " + this.pid +
                "\nDate: " + this.date +
                "\nBegin: " + this.begin +
                "\nEnd: " + this.end +
                "\nDescription: " + this.description +
                "\nRemarks: " + this.remarks + "\n";
    }
}
