// Paketanweisung und Importe
package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.CaregiverDao;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.TreatmentDao;
import de.hitec.nhplus.model.ActiveAcount;
import de.hitec.nhplus.model.Caregiver;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.utils.DateConverter;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Der <code>NewTreatmentController</code> verwaltet die Ansicht zum Hinzufügen einer neuen Behandlung.
 * Er stellt Methoden zum Einfügen und Abbrechen des Vorgangs bereit.
 */
public class NewTreatmentController {

    // Listen zur Verwaltung der Pflegekräfte und deren Namen
    private final ObservableList<Caregiver> caregivers = FXCollections.observableArrayList();
    private final ObservableList<String> caregiverSelection = FXCollections.observableArrayList();

    public Button buttonCancel;
    @FXML
    private ComboBox<String> comboBoxCaregiverSelection; //Injizieren der ComboBox
    // FXML-Felder, die mit den FXML-Elementen im zugehörigen FXML-Datei verbunden sind
    @FXML
    private Label labelFirstName;
    @FXML
    private Label labelSurname;
    @FXML
    private Label labelPhoneNumber;
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
    @FXML
    private Button buttonAdd;

    // Controller- und Patientenreferenzen sowie Stage-Referenz
    private AllTreatmentController controller;
    private Patient patient;
    private Stage stage;

    /**
     * Initialisiert den Controller mit den benötigten Referenzen und Einstellungen.
     *
     * @param controller Referenz zum übergeordneten Controller
     * @param stage      Referenz zur aktuellen Stage
     * @param patient    Patient, für den die Behandlung hinzugefügt wird
     */
    public void initialize(AllTreatmentController controller, Stage stage, Patient patient) {
        this.controller = controller;
        this.patient = patient;
        this.stage = stage;

        // Button "Add" deaktivieren, bis gültige Eingaben vorhanden sind
        this.buttonAdd.setDisable(true);
        // Listener für Eingabefelder hinzufügen, um die Aktivierung des "Add"-Buttons zu überwachen
        ChangeListener<String> inputNewPatientListener = (observableValue, oldText, newText) -> NewTreatmentController.this.buttonAdd.setDisable(NewTreatmentController.this.areInputDataInvalid());
        this.textFieldBegin.textProperty().addListener(inputNewPatientListener);
        this.textFieldEnd.textProperty().addListener(inputNewPatientListener);
        this.textFieldDescription.textProperty().addListener(inputNewPatientListener);
        this.textAreaRemarks.textProperty().addListener(inputNewPatientListener);
        this.datePicker.setValue(LocalDate.now());
        this.datePicker.valueProperty().addListener((observableValue, localDate, t1) -> NewTreatmentController.this.buttonAdd.setDisable(NewTreatmentController.this.areInputDataInvalid()));
        comboBoxCaregiverSelection.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> buttonAdd.setDisable(areInputDataInvalid()));


        // Konverter für die Umwandlung von LocalDate zu String und umgekehrt für den DatePicker setzen
        this.datePicker.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate localDate) {
                return (localDate == null) ? "" : DateConverter.convertLocalDateToString(localDate);
            }

            @Override
            public LocalDate fromString(String localDate) {
                return DateConverter.convertStringToLocalDate(localDate);
            }
        });
        // Patientendaten anzeigen und Pflegekräfte-Daten laden
        this.showPatientData();
        createComboBoxData();
    }

    /**
     * Zeigt die Patientendaten in den entsprechenden Labels an.
     */
    private void showPatientData() {
        this.labelFirstName.setText(patient.getFirstName()); // Setzen des Vornamens Patienten
        this.labelSurname.setText(patient.getSurname()); // Setzen des Nachnamens des Patienten
    }

    /**
     * Befüllt die ComboBox mit den Pflegekräften.
     */
    private void createComboBoxData() {
        CaregiverDao dao = DaoFactory.getDaoFactory().createCaregiverDAO();
        try {
            ArrayList<Caregiver> caregiverList = (ArrayList<Caregiver>) dao.readAll();
            for (Caregiver caregiver : caregiverList) {
                caregivers.add(caregiver);
                caregiverSelection.add(caregiver.getName());
            }
            comboBoxCaregiverSelection.setItems(caregiverSelection);
            ActiveAcount activeAccount = ActiveAcount.getInstance(null);
            if (activeAccount != null) {
                for (String caregiver : caregiverSelection) {
                    if (activeAccount.getName().equals(caregiver)) {
                        comboBoxCaregiverSelection.getSelectionModel().select(caregiverSelection.indexOf(caregiver));
                    }
                }
            }
            handleComboBoxCaregivers();
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
    }

    /**
     * Event-Handler-Methode für die Auswahl einer Pflegekraft in der ComboBox.
     * Diese Methode wird aufgerufen, wenn der Benutzer eine Pflegekraft auswählt.
     */
    @FXML
    public void handleComboBoxCaregivers() {
        String selectedCaregiverName = this.comboBoxCaregiverSelection.getSelectionModel().getSelectedItem();
        if (selectedCaregiverName != null) {
            for (Caregiver caregiver : caregivers) {
                if ((caregiver.getSurname() + ", " + caregiver.getFirstName()).equals(selectedCaregiverName)) {
                    this.labelPhoneNumber.setText(caregiver.getPhoneNumber());
                    break;
                }
            }
        }
    }

    /**
     * Event-Handler-Methode für das Hinzufügen einer neuen Behandlung.
     * Diese Methode wird aufgerufen, wenn der Benutzer auf "Add" klickt.
     */
    @FXML
    public void handleAdd() {
        LocalDate date = this.datePicker.getValue(); // Abrufen des ausgewählten Datums
        LocalTime begin = DateConverter.convertStringToLocalTime(textFieldBegin.getText()); // Konvertieren der eingegebenen Startzeit
        LocalTime end = DateConverter.convertStringToLocalTime(textFieldEnd.getText()); // Konvertieren der eingegebenen Endzeit
        String description = textFieldDescription.getText(); // Abrufen der eingegebenen Behandlungsbeschreibung
        String remarks = textAreaRemarks.getText(); // Abrufen der eingegebenen Bemerkungen
        boolean locked = false; // Standardwert für das "locked" Feld
        String selectedCaregiverName = comboBoxCaregiverSelection.getSelectionModel().getSelectedItem(); // Ausgewählte Pflegekraft abrufen
        long cid = -1;  // Initialisieren mit einem ungültigen Wert
        if (selectedCaregiverName != null) {
            for (Caregiver caregiver : caregivers) {
                if ((caregiver.getSurname() + ", " + caregiver.getFirstName()).equals(selectedCaregiverName)) {
                    cid = caregiver.getCid();// ID der Pflegekraft abrufen
                    break;
                }
            }
        }
        Treatment treatment = new Treatment(patient.getPid(), date, begin, end, description, remarks, locked, cid); // Erstellen eines neuen Behandlungsobjekts
        createTreatment(treatment); // Behandlung in der Datenbank erstellen
        controller.readAllAndShowInTableView(); // Alle Behandlungen neu laden und in der Tabelle anzeigen
        stage.close(); // Fenster schließen
    }

    /**
     * Erstellt eine neue Behandlung in der Datenbank.
     *
     * @param treatment Die zu erstellende Behandlung
     */
    private void createTreatment(Treatment treatment) {
        TreatmentDao dao = DaoFactory.getDaoFactory().createTreatmentDao(); // DAO-Objekt für Behandlung erstellen
        try {
            dao.create(treatment); // Neue Behandlung in der Datenbank erstellen
        } catch (SQLException exception) {
            System.setErr(System.err); // Fehlerbehandlung bei Datenbankfehlern
        }
    }

    /**
     * Event-Handler-Methode für das Abbrechen des Hinzufügens einer neuen Behandlung.
     * Diese Methode wird aufgerufen, wenn der Benutzer auf "Cancel" klickt.
     */
    @FXML
    public void handleCancel() {
        stage.close();
    } // Fenster schließen

    /**
     * Überprüft, ob die Eingabedaten ungültig sind.
     *
     * @return true, wenn die Eingabedaten ungültig sind, sonst false
     */
    private boolean areInputDataInvalid() {
        // Schaut ob mindestens ein der textfelder nicht beschrieben ist.
        if (textFieldBegin.getText().isEmpty() || textFieldEnd.getText().isEmpty() || textAreaRemarks.getText().isEmpty() || textFieldDescription.getText().isEmpty()) {
            return true;
        }

        // Wenn Start- oder Endzeit nicht eingegeben wurden, sind die Daten ungültig
        try {
            LocalTime begin = DateConverter.convertStringToLocalTime(textFieldBegin.getText());
            LocalTime end = DateConverter.convertStringToLocalTime(textFieldEnd.getText());
            if (!end.isAfter(begin)) {
                return true;
            }
        } catch (Exception exception) {
            return true;
        }

        // Schaut ob ein Angestellter ausgewählt wurde und ob das Datum gesetzt ist..
        return comboBoxCaregiverSelection.getSelectionModel().getSelectedItem() == null || datePicker.getValue() == null;
    }
}