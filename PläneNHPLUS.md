Aufgaben Reihenfolge

1. Löschen Button durch Sperren ersetzen:

   1.1. [x] Neue Tabellen für Gesperrte Daten (Patients, Behandlungen, Pfleger)
   1.2. [x] Löschen Button Text durch Sperren ersetzen
   1.3. [] Funktionalität von Löschen ändern von Löschen auf verschieben der Daten in neue Tabelle (In originaler Tabelle entfernen, in neue Einfügen)
   1.4. [] Wenn Patient gesperrt wird, müssen alle Behandlungen mit derselben Patient ID (PID) auch gesperrt werden.
   1.5. [] Wenn ein Pfleger gelöscht werden soll, muss geschaut werden, ob die letzte Behandlung des Angestellten weniger als 10 Jahre zurück liegt.
      a. [] Wenn ja, kann der Angestellt und seine Daten direkt gelöscht werden.
      b. [] Wenn nein, müssen die Daten des Angestellten so lange aufbewahrt werden (im gesperrten Zustand) bis die letzte Behandlung gelöscht wird (hier: Nach 10 Jahren, nach der Behandlung).
   1.6. [] Behandlungen an sich können nicht über einen Button gesperrt / gelöscht werden.
      a. [] Button aus Behandlungen entfernen, da Funktionalität über die Löschung des Patienten läuft

2. []Login hinzufügen 
   2.1. [] Jeder Angestellte / Eigentümer bekommt einen Login
   2.2. [] Je nach Job bekommt die Person verschiedene Berechtigungen
      2.2.1. [] Der Eigentümer -
         a. Darf Sich alle Daten ansehen, inklusive gesperrten.
         b. Darf keine Patienten / Behandlungen erstellen und / oder bearbeiten
         c. Darf neue Pfleger erstellen
         d. Darf Pfleger löschen (Siehe Voraussetzungen oben)
         e. Darf Pfleger bearbeiten (LOG!!!)
      2.2.2. [] Krankenschwester -
         a. Bekommt die Berechtigung sich Aktuelle Patienten und Behandlungen anzuschauen.
         b. Dürfen Behandlungen erstellen
         c. Dürfen selbsterstellte Behandlungen Bearbeiten (LOG!!!)
         d. Dürfen Patienten Daten Bearbeiten (LOG!!!)
         e. Darf keine Pfleger Daten bearbeiten.
      2.2.3. [] Arzt -
         a. Darf Sich alle Daten ansehen, inklusive gesperrten.
         b. Darf Behandlungen erstellen
         c. Darf ALLE aktuellen Behandlungen bearbeiten (LOG!!!)
         d. Darf keine Pfleger Daten bearbeiten.
   2.3. [] Login soll bei Programmstart angezeigt werden anstatt Übersicht.
      2.3.1. [] Legt aktuelle Pfleger ID fest für automatische Eintragung in den Log ein:
         a. Bei erstellen
         b. Bei Bearbeiten
         c. Bei Sperren
         ... Von Behandlungen und Patientendaten

3. [x] Neue Tabelle für Angestellte mit deren:
   3.1. PflegerID
   3.2. Vorname
   3.3. Nachname
   3.4. Telefonnummer

4. [] Hover / Button für Informationen
   4.1. [] Jedes Fenster / Seite muss einen kleinen Button haben
   4.1. [] Bei Hover / Klicken muss dieser eine kurze Beschreibung der Seite und ihrer Funktionalität anzeigen.

5. []LOG!!!!
   5.1. Jedes Erstellen einer/s:
      a. [] Patienten
      b. [] Behandlung
      c. [] Pfleger
   5.2. Jedes Bearbeiten einer/s:
      a. [] Patienten
      b. [] Behandlung
      c. [] Pfleger
   5.3. Jedes Löschen / Sperren einer/s:
      a. [] Patienten
      b. [] Behandlung
      c. [] Pfleger
      ...Muss in einen an dem jeweiligen Patienten / Behandlung / Pfleger verbundenen LOG eingetragen werden.
   5.4. [] Der LOG wird mit dem Löschen der jeweiligen Patienten / Behandlung / Pfleger mitgelöscht, NICHT wenn diese gesperrt werden.
