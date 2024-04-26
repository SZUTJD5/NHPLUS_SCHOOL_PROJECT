Aufgaben Reihenfolge

1. Löschen Button durch Sperren ersetzen:
   
   1. Neue Tabellen für Gesperrte Daten (Patients, Behandlungen, Staff)
   
   2. Löschen Button Text durch Sperren ersetzen
   
   3. Funktionalität von Löschen ändern von Löschen auf verschieben der Daten in neue Tabelle (In originaler Tabelle entfernen, in neue einfügen)
   
   4. Wenn Patient gesperrt wird, müssen alle Behandlungen mit der selben Patient ID (PID) auch gesperrt werden.
   
   5. Wenn Staff gelöscht werden soll, muss geschaut werden ob die letzte Behandlung des Angestellten weniger als 10 Jahre zurück liegt.
      
      1. Wenn ja, kann der Angestellt und seine Daten direkt gelöscht werden.
      
      2. Wenn nein, müssen die Daten des Angestellten solange aufbewahrt werden (im gesperrten Zustand) bis die letzte Behandlung gelöscht wird (Hier: Nach 10 Jahren, nach der Behandlung).
   
   6. Behandlungen an sich können nicht über einen Button gesperrt / gelöscht werden.
      
      1. Button aus Behandlungen entfernen, da funktionalität über die Löschung des Patienten läuft

2. Login hinzufügen
   
   1. Jeder Angestellte / Eigentümer bekommt einen Login
   2. Je nach Job bekommt die Person verschiedene Berechtigungen
      1. Eigentümer - 
         1. Darf Sich alle Daten ansehen, inklusive gesperrten.
         2. Darf keine Patienten / Behandlungen erstellen und / oder bearbeiten
         3. Darf neue Pfleger erstellen
         4. Darf Pfleger löschen (Siehe Vorraussetzungen oben)
         5. Darf Pfleger bearbeiten (LOG!!!)
      2. Krankenschwester - 
         1. Bekommt die Berechtigung sich Aktuelle Patienten und Behandlungen anzuschauen.
         2. Dürfen Behandlungen erstellen
         3. Dürfen selbsterstellte Behandlungen Bearbeiten (LOG!!!)
         4. Dürfen Patienten Daten Bearbeiten (LOG!!!)
         5. Darf keine Pflegerdaten bearbeiten.
      3. Arzt -
         1. Darf Sich alle Daten ansehen, inklusive gesperrten.
         2. Darf Behandlungen erstellen
         3. Darf ALLE aktuellen Behandlungen bearbeiten (LOG!!!)
         4. Darf keine Pflegerdaten bearbeiten.
   3. Login soll bei Programmstart angezeigt werden antatt Übersicht.
      1. Legt aktuelle StaffID fest für automatische Eintragung in den Log ein:
         1. Bei erstellen
         2. Bei Bearbeiten
         3. Bei Sperren
            
            ... Von Behandlungen und Patientendaten

3. Neue Tabelle für Angestellte mit deren:
   
   1. PflegerID
   
   2. Vorname
   
   3. Nachname
   
   4. Telfonnummer

4. Hover / Button für Informationen
   
   1. Jedes Fenster / Seite muss einen kleinen Button haben
      
      1. Bei Hover / Klicken muss dieser eine kurze Beschreibung der Seite und ihrer funktionalität anzeigen.

5. LOG!!!!
   
   1. Jedes Erstellen einer/s:
      
      1. Patienten
      
      2. Behandlung
      
      3. Pfleger
   
   2. Jedes Bearbeiten einer/s:
      
      1. Patienten
      
      2. Behandlung
      
      3. Pfleger
   
   3. Jedes Löschen / Sperren einer/s:
      
      1. Patienten
      
      2. Behandlung
      
      3. Pfleger
      
      ..Muss in einen an der jeweiligen Patienten / Behandlung / Pleger verbundenen LOG eingetragen werden.
   
   4. Der LOG wird mit dem Löschen der jeweiligen Patienten / Behandlung / Pleger mitgelöscht, NICHT wenn diese gesperrt werden.
