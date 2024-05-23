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
* Login wird nach Löschen des Pflegers gelöscht.

**Testfälle:**

| **User-Story** | **Akzeptanzkriterien** | **Tasks** | **Testfälle** | **Ergebnis**                                                                                                                                                                                                                                                                                                                                                                                            |
|----------------|------------------------|-----------|---------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Als Arzt möchte ich die am Patienten verübten Behandlungen und Medikamente angeben und einsehen, außerdem wäre es schön, wenn ich Medikamenten Behandlungen vorplanen kann, damit die Schwestern wissen, wann und was der Patient an Medizin bekommen soll, dies würde mir auch etwas Zeit sparen und mehr für mehr Patienten offenlassen. Dazu würde ich gerne auch eingetragene Behandlungen abändern können, aber nur ich sollte das machen können. | A_1: Behandlungen sollen einsehbar sein<br>A_2: Behandlungen sollen vorgeplant werden können<br>A_3: Nur Berechtigte dürfen Behandlungseinträge ändern können | T_1: „Alle“ - Selektor bei Behandlungen muss funktionieren.<br>T_2: User Accounts erstellen / mit Login<br>T_3: Berechtigungen zum Bearbeiten von Einträgen vergeben und kontrollieren beim Versuch. | TF1_:<br>Vorbedingung: Eine Behandlung wurde angelegt<br>auszuführende Testschritte: Änderung des Eintrags versuchen von berechtigen Account und von unberechtigten.<br>erwartetes Ergebnis: Berechtigter Account darf Eintrag ändern, während unberechtigter das nicht kann. | 1. Behandlungen können einsgesehen werden <br>2. Behandlungen können nicht vorgeplant werden <br>3. Es gibt keine Berechtigungen, jeder kann alles einsehen                                                                                                                                                                                                                                             |
| Als Versicherungsvertreter möchte ich das jeder Eintrag und jede Änderung zeigt, von wem Sie getätigt wurde, um bei einem Zwischenfall feststellen zu können wer was angeordnet und ausgeführt hat. (Kein Direkter Zugriff auf das Programm im Krankenhaus, das Programm lokal und die Fallrelevanten Daten können als log zum Importieren zugeschickt werden. (Nach möglicher Zensierung anderer Patienten Daten zum Datenschutz)) | A_1: Eintragungen und Änderungen sollen zeigen, von wem Sie getätigt wurden. | T_1: Jede Behandlung muss eine Art Log haben, um später einzusehen, wer sie erstellt und wer sie abgeändert hat und was. | TF1_:<br>Vorbedingung: Behandlung und Log wurden angelegt<br>auszuführende Testschritte: Behandlung abändern<br>erwartetes Ergebnis: Log zeigt an, dass Person die Behandlung geändert hat. | 1. Es gibt keinen Log oder andere Were einzusehen welcher Account welche Änderung vorgenommen hat                                                                                                                                                                                                                                                                                                       |
| Als Pflegerin möchte ich meine begrenzte Zeit nicht für die lästigen Dokumentationen verbrauchen. Ich würde mir wünschen, dass ich bei Daily Things Vorlagen für die Dokumentation hätte, um die eingesparte Zeit für die Patienten zu nutzen. Dazu wäre es praktisch, wenn ich sehen könnte, wie viel Zeit ich für den jeweiligen Patienten habe. Da ich nicht so IT-Affin bin, würde ich es auch gut finden, wenn ich auf jeder Seite, auf der ich mich im Programm befinde, einen Hover Button (?) habe der mir ein kurzen Hilfe Text anzeigt. | A_1: Auf jeder Seite muss ein Hover Button sein, mit einer kurzen Beschreibung<br>A_2: Bei der Dokumentation soll es die Möglichkeit geben einen vorgeschriebenen Text auswählen zu können und ihn manuell zu ändern<br>A_3: Der Behandlung soll die verfügbare Zeit angezeigt werden | T_1: Auf jeder Seite soll ein Hover Button hinzugefügt werden.<br>T_2: Es müssen zu jeder Seite kurze Texte geschrieben werden, die beschreiben, was man auf der Seite machen kann.<br>T_3: Beim Hover des Buttons soll ein kurzer Text erscheinen, der erklärt, was man auf der Seite alles machen kann.<br>T_4: In der Dokumentation soll es ein Feld mit vorgefertigten Dokumentationstexten geben.<br>T_5: Die Dokumentationstexte sollen manuell geändert werden können.<br>T_6: Bei der Behandlungsseite soll es ein Timer geben, der anzeigt, wie lange man noch Zeit hat. | TF1_:<br>Vorbedingung: Auf jeder Seite wurden Hover Buttons angelegt und mit kurzen Texten beschrieben<br>auszuführende Testschritte: Über den Hover Button hovern<br>erwartetes Ergebnis: Beim Hover des Buttons wird ein Text erscheinen, der beschreibt, was man auf der Seite alles machen kann.<br>TF2_:<br>Vorbedingung: In der Dokumentation soll es ein Feld geben, wo man vorgefertigte Dokumentationstexte auswählen kann<br>auszuführende Testschritte: In der Dokumentation auf das Feld klicken und ein vorgefertigter Text auswählen (z.B Blutdruck messen)<br>erwartetes Ergebnis: Ausgewählter Text (Blutdruck messen) wird in der Dokumentation angezeigt.<br>TF3_:<br>Vorbedingung: In der Dokumentation soll es ein Feld geben, wo man vorgefertigte Dokumentationstexte auswählen und diesen Text manuell ändern kann<br>auszuführende Testschritte: In der Dokumentation auf das Feld klicken und ein vorgefertigter Text auswählen (z.B Blut nehmen) und mit manuellen Daten (z.B xxxml Blut genommen) ändern<br>erwartetes Ergebnis: Ausgewählter Text (Blut nehmen) wird in der Dokumentation angezeigt und kann manuell geändert werden.<br>TF4_:<br>Vorbedingung: Es muss ein Timer geben, der anzeigt, wie lange man Zeit hat<br>auszuführende Testschritte: Es soll im Behandlungsbereich angeguckt werden, wie lange man Zeit hat<br>erwartetes Ergebnis: Es wird angezeigt, wie lange man Zeit hat. | 1. Der Button wurde erstellt und für jede Seite ein Beschreibungstext eingefügt <br>2. Es gibt keine Auswählmöglichkeit für vorgeschriebene Texte <br> 3. Es wird keine verfügbare Zeit für weitere Behandlungen angezeit                                                                                                                                                                               |
| Als Wohnbereichsleiter möchte ich eine übersichtliche Einsicht darüber haben, welcher Pflegebedarf bei welchem Patienten besteht, damit ich für entsprechende Gerätschaften besser planen kann. Dazu möchte ich die Behandlungen basierend auf einzelnen Unterkategorien (beispielsweise Pflegegrad oder ähnliche Behandlungsgeräte) clustern können, da ich so einfacher nach Behandlungen mit ähnlichem Equipment filtern kann. | A_1: Für die Patientenbehandlungen soll es eine übersichtliche Ansicht geben.<br>A_2: Ich kann die Daten in der Ansicht nach bestimmten Kategorien, wie Pflegegrad oder Beschreibung sortieren. | T_1: Eine entsprechende View muss existieren.<br>T_2: Daten müssen entsprechend bestimmter Kategorien angeordnet werden können. | TF1_:<br>Vorbedingung: Eine entsprechende Ansicht muss existieren<br>auszuführende Testschritte: Ich sortiere die Einträge nach auf Basis des Pflegegrads<br>erwartetes Ergebnis: Die Daten werden entsprechend richtig sortiert. | 1. Es wurde eine Übersicht für alle / individuelle Behandlungen der Patienten angelegt, außerdem können die einzelnen Behandlungen augerufen werden um weitere Informationen einzusehen. <br>2. Die Tabelle kann nach diesen Kategories sortiert werden, indem oben wo diese stehen auf die texte wie "pflegegrad" etc. geklickt wird. Auf oder abgsteigend je nachdem wie oft auf diese geklickt wird. |
| Als Eigentümer möchte ich eine mit deutschem Recht konforme Datenspeicherung in meiner Software haben, die genau aufschlüsselt, welche Behandlungen an welchem Patienten zu welchem Zeitpunkt durchgeführt wurden. Das Ganze soll nach Möglichkeit nicht nur lokal, sondern in einer Art Cloud gespeichert werden, um den Zugriff bei Bedarf zu erleichtern. | A_1: Datenspeicherung in einer Datenbank nach DSGVO Regeln<br>A_2: Cloudanbindung | T_1: Jede Behandlung muss mit einem Patienten verknüpft sein sowie Pfleger und behandelnder Arzt<br>T_2: Jeder Datensatz muss verschlüsselt in einer Datenbank gespeichert werden.<br>T_3: Die Datenbank muss mit einer Cloud synchronisiert werden. | TF1_:<br>Vorbedingung: SQL-Connection muss etabliert sein<br>auszuführende Testschritte: Einen Eintrag in der Software erstellen und prüfen, ob er in der Datenbank übernommen wurde<br>erwartetes Ergebnis: Eingetragene Daten tauchen korrekt in der Datenbank auf. | 1. Die Daten werden in einer Datenbank gespeichert. Die Daten können erst eingesehen werden nachdem man sich über einen Login angemeldet hat, die Passwörter werder verschlüsselt gespeichert. <br> 2. Es gibt keine Cloudanbindung.                                                                                                                                                                    |


Benutzername und Passwort für das Login:
1. LoginName: Bernd,Heidemann Passwort: SZut-01?
2. LoginName: Jannis,Dinklage Passwort: Ha77o!%e (Kein Pfleger)
3. LoginName: Rajbir,Singh    Passwort: kv&!,?w