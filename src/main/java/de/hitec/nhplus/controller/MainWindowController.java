// Paketanweisung und Importe
package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.model.ActiveAcount;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Der <code>MainWindowController</code> verwaltet das Hauptfenster der Anwendung.
 * Er bietet Methoden zum Anzeigen verschiedener Ansichten und zeigt eine Begrüßungsnachricht an.
 */
public class MainWindowController {

    // Textfeld zur Anzeige einer Begrüßungsnachricht
    @FXML
    public Text HalloText;
    // Haupt-BorderPane des Fensters
    @FXML
    private BorderPane mainBorderPane;

    /**
     * Initialisiert den Controller.
     * Diese Methode wird aufgerufen, nachdem alle FXML-Elemente geladen wurden.
     * Setzt die Begrüßungsnachricht basierend auf den Informationen des aktuell angemeldeten Benutzers.
     */
    @FXML
    private void initialize(){
        ActiveAcount activeAccount = ActiveAcount.getInstance(null);
        HalloText.setText("Hallo " + activeAccount.getFirstName() + "!");
    }

    /**
     * Handler-Methode zum Anzeigen aller Patienten.
     * Diese Methode wird aufgerufen, wenn der Benutzer die Option zum Anzeigen aller Patienten auswählt.
     * Lädt die Patientenansicht in das zentrale Bereich des Hauptfensters.
     */
    @FXML
    private void handleShowAllPatient() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllPatientView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            System.setErr(System.err);
        }
    }

    /**
     * Handler-Methode zum Anzeigen aller Behandlungen.
     * Diese Methode wird aufgerufen, wenn der Benutzer die Option zum Anzeigen aller Behandlungen auswählt.
     * Lädt die Behandlungsansicht in das zentrale Bereich des Hauptfensters.
     */
    @FXML
    private void handleShowAllTreatments() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllTreatmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            System.setErr(System.err);
        }
    }

    /**
     * Handler-Methode zum Anzeigen aller Pflegekräfte.
     * Diese Methode wird aufgerufen, wenn der Benutzer die Option zum Anzeigen aller Pflegekräfte auswählt.
     * Lädt die Pflegekräfteansicht in das zentrale Bereich des Hauptfensters.
     */
    @FXML
    public void handleShowAllCaregivers() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllCaregiverView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            System.setErr(System.err);
        }
    }
}
