// Paketanweisung und Importe
package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

// Controller-Klasse für das Hauptfenster der Anwendung.
public class MainWindowController {

    @FXML
    private BorderPane mainBorderPane;

    /**
     * Handler-Methode zum Anzeigen aller Patienten.
     *
     * @param event Das ActionEvent, das den Aufruf ausgelöst hat.
     */
    @FXML
    private void handleShowAllPatient(ActionEvent event) {
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
     * @param event Das ActionEvent, das den Aufruf ausgelöst hat.
     */

    @FXML
    private void handleShowAllTreatments(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllTreatmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            System.setErr(System.err);
        }
    }

    @FXML
    public void handleShowAllCaregivers(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllCaregiverView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            System.setErr(System.err);
        }
    }
}
