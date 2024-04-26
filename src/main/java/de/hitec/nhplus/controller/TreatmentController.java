// Paketanweisung und Importe
package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.PatientDao;
import de.hitec.nhplus.datastorage.TreatmentDao;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.utils.DateConverter;

import java.sql.SQLException;
import java.time.LocalDate;

// Behandlungs-Controller-Klasse
public class TreatmentController {

    // FXML-Felder, die mit den FXML-Elementen im zugehörigen FXML-Datei verbunden sind
    @FXML
    private Label labelPatientName;

    @FXML
    private Label labelCareLevel;

    @FXML
    private TextField textFieldBegin;

    @FXML
    private TextField textFieldEnd;

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextArea textAreaRemarks;

    @FXML
    private DatePicker datePicker;

    // Controller- und Stage-Referenzen sowie Patient- und Behandlungsobjekte
    private AllTreatmentController controller;
    private Stage stage;
    private Patient patient;
    private Treatment treatment;

    // Initialisierungsmethode des Controllers
    public void initializeController(AllTreatmentController controller, Stage stage, Treatment treatment) {
        this.stage = stage;
        this.controller = controller;
        PatientDao pDao = DaoFactory.getDaoFactory().createPatientDAO();
        try {
            this.patient = pDao.read((int) treatment.getPid());
            this.treatment = treatment;
            showData(); // Anzeigen der Daten des Patienten und der Behandlung
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    // Methode zum Anzeigen der Daten des Patienten und der Behandlung
    private void showData() {
        this.labelPatientName.setText(patient.getSurname() + ", " + patient.getFirstName()); // Patientenname anzeigen
        this.labelCareLevel.setText(patient.getCareLevel()); // Pflegestufe anzeigen
        LocalDate date = DateConverter.convertStringToLocalDate(treatment.getDate()); // Datum der Behandlung konvertieren
        this.datePicker.setValue(date); // Datum der Behandlung setzen
        this.textFieldBegin.setText(this.treatment.getBegin()); // Beginn der Behandlung setzen
        this.textFieldEnd.setText(this.treatment.getEnd()); // Ende der Behandlung setzten
        this.textFieldDescription.setText(this.treatment.getDescription()); // Beschreibung der Behandlung setzen
        this.textAreaRemarks.setText(this.treatment.getRemarks()); // Anmerkung der Behandlung setzen
    }

    // Event-Handler für Änderungen an den Behandlungsinformationen
    @FXML
    public void handleChange() {
        this.treatment.setDate(this.datePicker.getValue().toString()); // Datum aktualisieren
        this.treatment.setBegin(textFieldBegin.getText()); // Beginn aktualisieren
        this.treatment.setEnd(textFieldEnd.getText()); // Ende aktualisieren
        this.treatment.setDescription(textFieldDescription.getText()); // Beschreibung aktualisieren
        this.treatment.setRemarks(textAreaRemarks.getText()); // Anmerkungen aktualisieren
        doUpdate(); // Aktualisierung in der Datenbank durchführen
        controller.readAllAndShowInTableView(); // Alle Behandlungen neu lesen und in der Tabelle anzeigen
        stage.close(); // Stage schließen
    }

    // Methode zum Aktualisieren der Behandlungsinformationen in der Datenbank
    private void doUpdate() {
        TreatmentDao dao = DaoFactory.getDaoFactory().createTreatmentDao();
        try {
            dao.update(treatment); // Behandlungsinformationen aktualisieren
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    // Event-Handler für Abbruch des Bearbeitungsvorgangs
    @FXML
    public void handleCancel() {
        stage.close();
    } // Stage schließen
}