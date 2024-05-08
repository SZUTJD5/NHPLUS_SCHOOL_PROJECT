package de.hitec.nhplus.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Diese Klasse nimmt den im Programm in den jeweiligen Feldern eingegebenen String
 * und bearbeitet ihn.
 **/
public class DateConverter {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm";

    /**
     * Diese Methode wandelt den input String in das Format für das Lokale Datum um ind gibt dieses zurück.
     **/
    public static LocalDate convertStringToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    /**
     * Diese Methode wandelt den input String in das Format für die Lokale Zeit um und gibt diese zurück.
     **/
    public static LocalTime convertStringToLocalTime(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    /**
     * Diese Methode wandelt eine Objekt von LocalDate in einen String um und gibt diesen zurück.
     **/
    public static String convertLocalDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    /**
     * Diese Methode wandelt ein Objekt von LocalTime in einen String um und gibt diesen zurück.
     **/
    public static String convertLocalTimeToString(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }
}
