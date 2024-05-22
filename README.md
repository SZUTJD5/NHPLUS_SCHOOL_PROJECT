## Features:

**Dokumentation:**

* Javadoc wurde für den Code erstellt.

**Verbesserungen der Benutzeroberfläche:**

* Die Tab-Reihenfolge in den Fenstern wurde angepasst.
* Ein Hilfe-Hover-Button wurde hinzugefügt, der Informationen zu jeder Seite bereitstellt.

**Datenverwaltung:**

* Die Löschfunktion wurde durch eine Sperrfunktion ersetzt für:
    * Patienten
    * Behandlungen
    * Angestellte
* Gesperrte Daten bleiben in der Datenbank, werden aber nicht mehr angezeigt.
* Patienten und Behandlungen werden nach 10 Jahren nach dem Erstelldatum gelöscht, wenn sie gesperrt sind.
* Angestellte werden gelöscht, wenn sie:
    * Gesperrt sind und:
        * Ihre neueste Behandlung mehr als 10 Jahre alt ist oder
        * Sie mit keiner Behandlung mehr verbunden sind.
    * Gesperrt sind und:
        * Ihre letzte verknüpfte Behandlung gelöscht wird oder
        * Ihre letzte verknüpfte Behandlung 10 Jahre alt wird.
* Behandlungen eines Patienten werden mit ihm zusammen gesperrt, wenn er gesperrt wird.

**Behandlungen:**

* Pfleger können in Behandlungen eingetragen werden.
* Handynummer von ausgewählten Pflegern werden automatisch in neue Behandlungen eingetragen.
* Einzelnes Löschen von Behandlungen ist nicht mehr möglich.
* Das Datum des aktuellen Tages wird im Fenster für neue Behandlungen automatisch ausgewählt.
* Wenn der aktuelle Benutzer ein Pfleger ist, wird er bei der Erstellung einer neuen Behandlung automatisch ausgewählt.

**Pflegerverwaltung:**

* Eine Pflegertabelle wurde erstellt.
* Pfleger können erstellt werden.
* Die Pflegertabelle ist im Programm einsehbar.
* Jeder Pfleger erhält einen Login.
* Pfleger erhalten beim Erstellen ein Login und Passwort für das Programm.
* Die Handynummer des Pflegers darf nur aus Ziffern bestehen.

**Login-System:**

* Ein Login-System wurde erstellt.
* Das Login-Fenster wird beim Programmstart anstelle des MainWindowView angezeigt.
* Passwörter werden beim Erstellen eines Pflegers verschlüsselt in der Datenbank gespeichert.
* Beim Login wird das Passwort gehasht und mit dem Hash des gespeicherten Passworts verglichen, um den Login zu verifizieren.
* Das Passwort wird beim Login maskiert.
* Der Login vergleicht Benutzername und Passwort mit der Datenbank.
* Der Login legt den angemeldeten Pfleger fest.
* Login wird nach Löschen des Pflegers gelöscht


PASSWÖRTER ZUM LOGIN!
1. LoginName: Jannis,Dinklage Passwort: Ha77o!%e (Kein Pfleger)
2. LoginName: Rajbir,Singh    Passwort: kv&!,?w
3. LoginName: Bernd,Heidemann Passwort: SZut-01?
