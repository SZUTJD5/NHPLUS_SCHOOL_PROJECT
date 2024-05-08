package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.PatientDao;
import de.hitec.nhplus.datastorage.TreatmentDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllTreatmentController {

    public Button buttonNewTreament;

    // Zeigt die Behandlungsdaten an
    @FXML
    private TableView<Treatment> tableView;

    // Zeigt die Behandlungs ID's an
    @FXML
    private TableColumn<Treatment, Integer> columnId;

    // Zeigt die Patienten ID's an
    @FXML
    private TableColumn<Treatment, Integer> columnPid;

    // Zeigt das Datum der Behandlung an
    @FXML
    private TableColumn<Treatment, String> columnDate;

    // Zeit die Zeit an wann die Behandlung begonnen hat
    @FXML
    private TableColumn<Treatment, String> columnBegin;

    // Zeigt die Zeit an wann die Behandlung abgeschlossen wurde
    @FXML
    private TableColumn<Treatment, String> columnEnd;

    // Zeigt die Behandlungsbeschreibung an
    @FXML
    private TableColumn<Treatment, String> columnDescription;

    // ComboBox zur Auswahl von Patienten
    @FXML
    private ComboBox<String> comboBoxPatientSelection;

    // ObservableList Speichert die Behandlungen, welche in der TableView angezeigt werden sollen.
    private final ObservableList<Treatment> treatments = FXCollections.observableArrayList();
    private TreatmentDao dao;
    // Enthält die Patienten Namen
    private final ObservableList<String> patientSelection = FXCollections.observableArrayList();
    // Liste aller Patienten
    private ArrayList<Patient> patientList;

    // Initialisierung des Controllers
    public void initialize() {
        // Ließt alle Behandlungen aus und fügt sie ein
        readAllAndShowInTableView();
        // Richtet die ComboBox für die Patientennamen ein
        comboBoxPatientSelection.setItems(patientSelection);
        comboBoxPatientSelection.getSelectionModel().select(0);

        // Einrichtung der Spalten in der TableView
        this.columnId.setCellValueFactory(new PropertyValueFactory<>("tid"));
        this.columnPid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        this.columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.columnBegin.setCellValueFactory(new PropertyValueFactory<>("begin"));
        this.columnEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        this.columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        // Einfügung der Spalten in die TableView
        this.tableView.setItems(this.treatments);

        // Fügt die Patientennamen und den "alle" Selektor in das Dropdown menue zur auswahl der Patienten ein.
        this.createComboBoxData();
    }



    // Ließt alle Behandlungen aus der Datenbank aus und fügt sie der TableView an.
    public void readAllAndShowInTableView() {
        comboBoxPatientSelection.getSelectionModel().select(0);
        this.dao = DaoFactory.getDaoFactory().createTreatmentDao();
        try {
            this.treatments.clear();
            List<Treatment> retrievedTreatments = dao.readAll();
            this.treatments.addAll(retrievedTreatments);
        } catch (SQLException exception) {
            System.setErr(System.err);        }
    }

    // Befüllt die ComboBox mit den Patientennamen und dem "alle" Selektor
    private void createComboBoxData() {
        PatientDao dao = DaoFactory.getDaoFactory().createPatientDAO();
        try {
            patientList = (ArrayList<Patient>) dao.readAll();
            patientSelection.add("alle");
            for (Patient patient: patientList) {
                patientSelection.add(patient.getSurname());
            }
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
    }


    // Behandelt die selektierte ComboBox
    @FXML
    public void handleComboBox() {
        String selectedPatient = this.comboBoxPatientSelection.getSelectionModel().getSelectedItem();
        this.treatments.clear();
        this.dao = DaoFactory.getDaoFactory().createTreatmentDao();

        if (selectedPatient.equals("alle")) {
            try {
                this.treatments.addAll(this.dao.readAll());
            } catch (SQLException exception) {
                System.setErr(System.err);
            }
        }

        Patient patient = searchInList(selectedPatient);
        if (patient !=null) {
            try {
                this.treatments.addAll(this.dao.readTreatmentsByPid(patient.getPid()));
            } catch (SQLException exception) {
                System.setErr(System.err);
            }
        }
    }

    private Patient searchInList(String surname) {
        for (Patient patient : this.patientList) {
            if (patient.getSurname().equals(surname)) {
                return patient;
            }
        }
        return null;
    }

    /* // MIT SPERREN MECHANIK ERSETZEN!

    // Löscht die selektierte Zeile nach Klick auf den Löschen Button
    @FXML
    public void handleDelete() {
        int index = this.tableView.getSelectionModel().getSelectedIndex();
        Treatment t = this.treatments.remove(index);
        TreatmentDao dao = DaoFactory.getDaoFactory().createTreatmentDao();
        try {
            dao.deleteById(t.getTid());
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
    }

     */

    // Fügt eine neuen Behandlung ein. Nutzt die Daten aus den Eingabe Feldern.
    @FXML
    public void handleNewTreatment() {
        try{
            String selectedPatient = this.comboBoxPatientSelection.getSelectionModel().getSelectedItem();
            Patient patient = searchInList(selectedPatient);
            newTreatmentWindow(patient);
        } catch (NullPointerException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Patient für die Behandlung fehlt!");
            alert.setContentText("Wählen Sie über die Combobox einen Patienten aus!");
            alert.showAndWait();
        }
    }

    // Setzt die Zeile auf die geklickt wurde als selektiertes Element
    @FXML
    public void handleMouseClick() {
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (tableView.getSelectionModel().getSelectedItem() != null)) {
                int index = this.tableView.getSelectionModel().getSelectedIndex();
                Treatment treatment = this.treatments.get(index);
                treatmentWindow(treatment);
            }
        });
    }

    // Öffnet das Fenster für eine neue Behandlung
    public void newTreatmentWindow(Patient patient) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/NewTreatmentView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);

            // the primary stage should stay in the background
            Stage stage = new Stage();

            NewTreatmentController controller = loader.getController();
            controller.initialize(this, stage, patient);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException exception) {
            System.setErr(System.err);
        }
    }

    // Öffnet das Fenster einer bereits existierenden Behandlung zur auslesung der Details
    public void treatmentWindow(Treatment treatment){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/TreatmentView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);

            // the primary stage should stay in the background
            Stage stage = new Stage();
            TreatmentController controller = loader.getController();
            controller.initializeController(this, stage, treatment);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException exception) {
            System.setErr(System.err);
        }
    }
}
