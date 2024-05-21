// Paketanweisung und Importe
package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.model.ActiveAcount;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

// Controller-Klasse für das Hauptfenster der Anwendung.
public class MainWindowController {

    @FXML
    public Text HalloText;
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private void initialize(){
        ActiveAcount activeAccount = ActiveAcount.getInstance(null);
        HalloText.setText("Hallo " + activeAccount.getFirstName() + "!");
    }

    /**
     * Handler-Methode zum Anzeigen aller Patienten.
     *
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
     *
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
