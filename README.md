#NHPLUS 
Tasks:
1. - [x] Vermögendsstand muss etfernt werden, aus der Datenbank
2. - [ ] Möglichkeit einführen Behandlungen zu Sperren (Sperren Button einführen, für Patienten und alle ihre Daten, nicht einzelne Behandlungen)
4. - [ ] Alle Patientendaten sollten gleichzeitig Sperrbar sein -> Siehe 2
5. - [ ] Nach 10 Jahren dürfen Daten gelöscht werden. (Täglicher check bei gesperrten Behandlunge / Patienten ob Daten 10 Jahre als sind, wenn ja -> Löschen)
6. - [ ] Login einbauen - für berechtigte Personen (Jede Schwester und Jeder Facharzt erhält einen Login mit verschiedenen Berechtigungen)
7. - [ ] Pflegermodul ?
8. - [ ] Daten dürfen nicht einfach Löschbar sein. (Löschen button entfernen)

8. - [ ] Berechtigungen verteilen (z.b. um Behandlungseinträge zu bearbeiten / zu erstellen)
9. - [ ] Hover / Button für Informationen zu jeder Seite (Für Informationen zum Ausfüllen / etc)
10. - [ ] Behandlungen sollten einen LOG haben über die Person die sie erstellt hat und wer sie potentiell abgeändert hat / was geändert wurde. (Automatischen Log anfügen)
11. - [ ] JavaDoc erstellen und Codebschreibung hinzufügen

// Potentiell (nicht teil des MVP)
12. - [ ] Anklicktbare vorlagen für wiederkehrende tätigkeiten (Vorgeschriebene Texte werden eingefügt mit Rot makierten Bereichen zum Ausfüllen der Daten)
13. - [ ] Datenbanken müssen generell verschlüsselt sein und werden nach Login entschlüsselt. 
14. - [ ]Behandlungen sollten vorgeplant werden können. (Z.B. Alle 4 Stunden / Tage idc, Medikament B verabreichen. / Alle 4 Stunden IV Beutel tauschen etc etc)

Original Aufgabenstellung:

- -[x] Vermögensstand muss aus der Applikation entfernt werden
- -[ ] Behandlungen haben eine Sperrfrist von 10 Jahren
- -[ ] Falls die Daten nicht mehr benötigt werden, können sie gesperrt werden
- -[ ] Sperren heißt kein Delete!
- -[ ] Echtes Löschen erst nach 10 Jahren
- -[ ] Login
- -[ ] Nur berechtigte Personen (Pfleger) dürfen Zugriff haben
- -[ ] Pflegermodul (siehe Notizen mit dem Auftraggeber)
 
Notizen aus Auftraggeber Gespräch:

    Die Navigationsleiste am linken Rand der Anwendung soll einen weiteren Button bekommen, über den zu einer Ansicht gewechselt wird, 
    die alle Pfleger/Pflegerinnen mit ihrer ID, ihrem Nach- und Vornamen sowie ihrer Telefonnummer angezeigt werden.
    Diese Ansicht wurde bereits im letzten Sprint in der Datei AllCaregiverView.fxml fertiggestellt und wurde vom Auftraggeber so abgenommen).
    
    Die Anzeige einer einzelnen Behandlung soll um die Daten des Pflegers/in (der Name im Format Nachname, 
    Vorname sowie Telefon in einem eigenem Label) ergänzt werden, der/die die Behandlung durchgeführt hat.
    
    Beim Anlegen einer neuen Behandlung soll die entsprechende Pflegekraft mit Hilfe einer Combobox ausgewählt werden können.
    
    Beim Löschen einer Pflegekraft muss ebenfalls die 10 Jahresregelung berücksichtigt werden, d.h. nicht mehr aktive Pfleger werden auf Wunsch gesperrt, 
    sofern sie noch Behandlungen durchgeführt haben, die jünger als 10 Jahre sind. Sind nur noch Behandlungen, die 10 Jahre zurückliegen, wird die Pflegekraft gelöscht. 
    Die durchgeführten Behandlungen sollen weiter angezeigt werden. 