package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.DaoImp;
import de.hitec.nhplus.datastorage.PatientDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.utils.DateConverter;

import java.sql.SQLException;
import java.time.LocalDate;


/**
 * Der <code>AllPatientController</code> enthält die gesamte Logik der Patientenansicht. Er bestimmt, welche Daten angezeigt werden und wie auf Ereignisse reagiert wird.
 */
public class AllPatientController {

    // Zeigt die Patientendaten an
    @FXML
    private TableView<Patient> tableView;

    // Zeigt die Patienten ID's an
    @FXML
    private TableColumn<Patient, Integer> columnId;

    // Zeigt den Vornamen der Patienten an
    @FXML
    private TableColumn<Patient, String> columnFirstName;

    // Zeigt die Nachnamen der Patienten an
    @FXML
    private TableColumn<Patient, String> columnSurname;

    // Zeigt die Guburtsdaten der Patienten an
    @FXML
    private TableColumn<Patient, String> columnDateOfBirth;

    // Zeigt das Pflegelevel der Patienten an
    @FXML
    private TableColumn<Patient, String> columnCareLevel;

    // Zeit die Raumnummer der Patienten an
    @FXML
    private TableColumn<Patient, String> columnRoomNumber;

    // Button zum Löschen von Patienten
    @FXML
    private Button buttonDelete;

    // Button zum hinzufügen von Patienten
    @FXML
    private Button buttonAdd;

    // Textfeld zum Eintragen des Nachnamen
    @FXML
    private TextField textFieldSurname;

    // Textfeld zum eintragen des Vornamen
    @FXML
    private TextField textFieldFirstName;

    // Textfeld zum eintragen des Geburtsdautm
    @FXML
    private TextField textFieldDateOfBirth;

    // Textfeld zum eintragen des Pflegelevels
    @FXML
    private TextField textFieldCareLevel;

    // Textfeld zum eintragen der Zimmernummer
    @FXML
    private TextField textFieldRoomNumber;

    // ObservableList speichert die Patienten
    private final ObservableList<Patient> patients = FXCollections.observableArrayList();
    private PatientDao dao;

    /**
     * Wenn <code>initialize()</code> aufgerufen wird, sind alle Felder bereits initialisiert. Zum Beispiel vom FXMLLoader
     * nach dem Laden einer FXML-Datei. Zu diesem Zeitpunkt des Controller-Lebenszyklus können die Felder abgerufen und
     * konfiguriert werden.
     */
    public void initialize() {
        this.readAllAndShowInTableView();

        this.columnId.setCellValueFactory(new PropertyValueFactory<>("pid"));

        // CellValueFactory zeigt Eigenschaftswerte in der TableView an.
        this.columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        // CellFactory schreibt Eigenschaftswerte in die TableView
        this.columnFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.columnSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        this.columnDateOfBirth.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnCareLevel.setCellValueFactory(new PropertyValueFactory<>("careLevel"));
        this.columnCareLevel.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        this.columnRoomNumber.setCellFactory(TextFieldTableCell.forTableColumn());

        // Fügt die Daten in die TableView ein
        this.tableView.setItems(this.patients);

        this.buttonDelete.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldPatient, newPatient) -> AllPatientController.this.buttonDelete.setDisable(newPatient == null));

        this.buttonAdd.setDisable(true);
        ChangeListener<String> inputNewPatientListener = (observableValue, oldText, newText) ->
                AllPatientController.this.buttonAdd.setDisable(!AllPatientController.this.areInputDataValid());
        this.textFieldSurname.textProperty().addListener(inputNewPatientListener);
        this.textFieldFirstName.textProperty().addListener(inputNewPatientListener);
        this.textFieldDateOfBirth.textProperty().addListener(inputNewPatientListener);
        this.textFieldCareLevel.textProperty().addListener(inputNewPatientListener);
        this.textFieldRoomNumber.textProperty().addListener(inputNewPatientListener);
    }

    /**
     * Falls eine Zelle der Spalte mit den Vornamen geändert wurde, wird diese Methode aufgerufen, um die Änderung zu speichern.
     *
     * @param event Ereignis mit dem geänderten Objekt und der Änderung.
     */
    @FXML
    public void handleOnEditFirstname(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setFirstName(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * Falls eine Zelle der Spalte mit den Nachnamen geändert wurde, wird diese Methode aufgerufen, um die Änderung zu speichern.
     *
     * @param event Ereignis mit dem geänderten Objekt und der Änderung.
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setSurname(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * Falls eine Zelle der Spalte mit den Geburtsdaten geändert wurde, wird diese Methode aufgerufen, um die Änderung zu speichern.
     *
     * @param event Ereignis mit dem geänderten Objekt und der Änderung.
     */
    @FXML
    public void handleOnEditDateOfBirth(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setDateOfBirth(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * Falls eine Zelle der Spalte mit den Pflegestufen geändert wurde, wird diese Methode aufgerufen, um die Änderung zu speichern.
     *
     * @param event Ereignis mit dem geänderten Objekt und der Änderung.
     */
    @FXML
    public void handleOnEditCareLevel(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setCareLevel(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * Falls eine Zelle der Spalte mit den Zimmernummern geändert wurde, wird diese Methode aufgerufen, um die Änderung zu speichern.
     *
     * @param event Ereignis mit dem geänderten Objekt und der Änderung.
     */
    @FXML
    public void handleOnEditRoomNumber(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setRoomNumber(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * Updated einen Patienten, indem die Methode <code>update()</code> von {@link PatientDao} aufgerufen wird.
     *
     * @param event Ereignis mit dem geänderten Objekt und der Änderung.
     */
    private void doUpdate(TableColumn.CellEditEvent<Patient, String> event) {
        try {
            this.dao.update(event.getRowValue());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Schreibt alle Patienten in die Tabelle, indem die Liste aller Patienten gelöscht und erneut mit allen gespeicherten
     * Patienten, geliefert von {@link PatientDao}, gefüllt wird.
     */
    private void readAllAndShowInTableView() {
        this.patients.clear();
        this.dao = DaoFactory.getDaoFactory().createPatientDAO();
        try {
            this.patients.addAll(this.dao.readAll());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /*
        Neue Methode zum Sperren der Daten. Verschied die Daten aus dieser Tabelle in die "lockedpatient" Tabelle.
     */
    @FXML
    public void handleLock() {
        Patient selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                this.dao.updateLockStatus(selectedItem.getPid(), true); // Updated den "locked" Wert des Patients.
                readAllAndShowInTableView(); // Updatet die Tabelle im Programm
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }





    /**
     * Diese Methode behandelt Ereignisse, die vom Button zum Löschen von Patienten ausgelöst werden. Sie ruft {@link PatientDao} auf, um den
     * Patienten aus der Datenbank zu löschen und entfernt das Objekt aus der Liste, die die Datenquelle der
     * <code>TableView</code> ist.
     */

    @FXML
    public void handleDelete() {
        Patient selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                DaoFactory.getDaoFactory().createPatientDAO().deleteById(selectedItem.getPid());
                this.tableView.getItems().remove(selectedItem);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }


    /**
     * Diese Methode behandelt die Ereignisse, die durch den Button zum Hinzufügen eines Patienten ausgelöst werden. Sie sammelt die Daten von den
     * <code>TextField</code>s, erstellt ein Objekt der Klasse <code>Patient</code> und übergibt das Objekt an {@link PatientDao}, um die Daten zu speichern.
     */
    @FXML
    public void handleAdd() {
        String surname = this.textFieldSurname.getText();
        String firstName = this.textFieldFirstName.getText();
        String birthday = this.textFieldDateOfBirth.getText();
        LocalDate date = DateConverter.convertStringToLocalDate(birthday);
        String careLevel = this.textFieldCareLevel.getText();
        String roomNumber = this.textFieldRoomNumber.getText();
        String locked = "NO"; // Standart Wert, Patient nicht gesperrt bei erstellung.
        try {
            this.dao.create(new Patient(firstName, surname, date, careLevel, roomNumber, locked));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        readAllAndShowInTableView();
        clearTextfields();
    }

    /**
     * Löscht alle Inhalte aus allen vorhandenen <code>TextField</code>ern.
     */
    private void clearTextfields() {
        this.textFieldFirstName.clear();
        this.textFieldSurname.clear();
        this.textFieldDateOfBirth.clear();
        this.textFieldCareLevel.clear();
        this.textFieldRoomNumber.clear();
    }

    private boolean areInputDataValid() {
        if (!this.textFieldDateOfBirth.getText().isBlank()) {
            try {
                DateConverter.convertStringToLocalDate(this.textFieldDateOfBirth.getText());
            } catch (Exception exception) {
                return false;
            }
        }

        return !this.textFieldFirstName.getText().isBlank() && !this.textFieldSurname.getText().isBlank() &&
                !this.textFieldDateOfBirth.getText().isBlank() && !this.textFieldCareLevel.getText().isBlank();
    }
}
