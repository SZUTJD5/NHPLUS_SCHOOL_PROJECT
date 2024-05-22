package de.hitec.nhplus.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * <code>DateConverter</code> bietet Methoden zum Konvertieren von Datum- und Zeit-Strings in lokale Datum- und Zeitobjekte
 * sowie zum Konvertieren von lokalen Datum- und Zeitobjekten in Strings an.
 */
public class DateConverter {

    /**
     * Das Format für das Datum in den Strings.
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Das Format für die Zeit in den Strings.
     */
    private static final String TIME_FORMAT = "HH:mm";

    /**
     * Wandelt einen Datum-String in ein lokales Datumobjekt um und gibt dieses zurück.
     *
     * @param date Der Datum-String im Format "yyyy-MM-dd".
     * @return Das lokale Datumobjekt.
     */
    public static LocalDate convertStringToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    /**
     * Wandelt einen Zeit-String in ein lokales Zeitobjekt um und gibt dieses zurück.
     *
     * @param time Der Zeit-String im Format "HH:mm".
     * @return Das lokale Zeitobjekt.
     */
    public static LocalTime convertStringToLocalTime(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    /**
     * Wandelt ein lokales Datumobjekt in einen String um und gibt diesen zurück.
     *
     * @param date Das lokale Datumobjekt.
     * @return Der Datum-String im Format "yyyy-MM-dd".
     */
    public static String convertLocalDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}