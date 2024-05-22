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

/**
 * Die Klasse `TreatmentController` ist verantwortlich für die Steuerung der Benutzeroberfläche zur Anzeige und Bearbeitung von Behandlungsinformationen.
 */
public class TreatmentController {

    /**
     * Label für den Namen der Pflegekraft
     */
    public Label labelCaregiver;
    /**
     * Label für die Telefonnummer der Pflegekraft.
     */
    public Label labelPhoneNumber;
    /**
     * Button zum Ändern der Behandlungsinformationen
     */
    public Button btnChange;
    /**
     * Button zum Abbrechen der Bearbeitung
     */
    public Button btnCancel;

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

    /**
     * Initialisiert den Controller mit der übergebenen AllTreatmentController-Instanz, Stage und Behandlungsobjekt.
     *
     * @param controller Die AllTreatmentController-Instanz, zu der dieser Controller gehört.
     * @param stage      Die Stage, auf der die Benutzeroberfläche angezeigt wird.
     * @param treatment  Das Behandlungsobjekt, das bearbeitet werden soll.
     */
    public void initializeController(AllTreatmentController controller, Stage stage, Treatment treatment) {
        this.stage = stage;
        this.controller = controller;
        PatientDao pDao = DaoFactory.getDaoFactory().createPatientDAO();
        try {
            this.patient = pDao.read((int) treatment.getPid());
            this.treatment = treatment;
            showData(); // Anzeigen der Daten des Patienten und der Behandlung
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
    }

    /**
     * Zeigt die Daten des Patienten und der Behandlung in den entsprechenden Feldern der Benutzeroberfläche an.
     */
    private void showData() {
        this.labelPatientName.setText(patient.getSurname() + ", " + patient.getFirstName()); // Patientenname anzeigen
        this.labelCareLevel.setText(patient.getCareLevel()); // Pflegestufe anzeigen
        LocalDate date = DateConverter.convertStringToLocalDate(treatment.getDate()); // Datum der Behandlung konvertieren
        this.datePicker.setValue(date); // Datum der Behandlung setzen
        this.textFieldBegin.setText(this.treatment.getBegin()); // Beginn der Behandlung setzen
        this.textFieldEnd.setText(this.treatment.getEnd()); // Ende der Behandlung setzten
        this.textFieldDescription.setText(this.treatment.getDescription()); // Beschreibung der Behandlung setzen
        this.textAreaRemarks.setText(this.treatment.getRemarks()); // Anmerkung der Behandlung setzen
        // Haha 3 Stunden für diese 2 Label xD
        this.labelCaregiver.setText(this.treatment.retrieveCaregiver(this.treatment.getCid()).getName());
        this.labelPhoneNumber.setText(this.treatment.retrieveCaregiver(this.treatment.getCid()).getPhoneNumber());

    }


    /**
     * Aktualisiert die Behandlungsinformationen basierend auf den Benutzereingaben.
     */
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
            System.setErr(System.err);
        }
    }

    /**
     * Schließt die Stage und bricht den Bearbeitungsvorgang ab.
     */
    @FXML
    public void handleCancel() {
        stage.close();
    } // Stage schließen
}