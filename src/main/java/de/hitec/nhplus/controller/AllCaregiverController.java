package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.CaregiverDao;
import de.hitec.nhplus.model.Caregiver;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.SQLException;


/**
 * Der <code>AllCaregiverController</code> enthält die gesamte Logik der Angestelltenansicht. Er bestimmt, welche Daten angezeigt werden und wie auf Ereignisse reagiert wird.
 */
public class AllCaregiverController {

    // ObservableList speichert die Angestellten
    private final ObservableList<Caregiver> caregivers = FXCollections.observableArrayList();
    // Zeigt die Angestelltendaten an
    @FXML
    private TableView<Caregiver> tableView;
    // Zeigt die Angestellten ID's an
    @FXML
    private TableColumn<Caregiver, Integer> columnId;
    // Zeigt den Vornamen der Angestellten an
    @FXML
    private TableColumn<Caregiver, String> columnFirstName;
    // Zeigt die Nachnamen der Angestellten an
    @FXML
    private TableColumn<Caregiver, String> columnSurname;
    // Zeigt die Handynummer der Angestellten an
    @FXML
    private TableColumn<Caregiver, String> columnPhoneNumber;
    // Button zum Löschen von Angestellten
    @FXML
    private Button buttonDelete;
    // Button zum hinzufügen von Angestellten
    @FXML
    private Button buttonAdd;
    // Textfeld zum Eintragen des Nachnamen
    @FXML
    private TextField textFieldSurname;
    // Textfeld zum eintragen des Vornamen
    @FXML
    private TextField textFieldFirstName;
    // Textfeld zum eintragen der Handynummer
    @FXML
    private TextField textFieldPhoneNumber;
    private CaregiverDao dao;

    /**
     * Wenn <code>initialize()</code> aufgerufen wird, sind alle Felder bereits initialisiert. Zum Beispiel vom FXMLLoader
     * nach dem Laden einer FXML-Datei. Zu diesem Zeitpunkt des Controller-Lebenszyklus können die Felder abgerufen und
     * konfiguriert werden.
     */
    public void initialize() {
        this.readAllAndShowInTableView();

        this.columnId.setCellValueFactory(new PropertyValueFactory<>("cid"));

        // CellValueFactory zeigt Eigenschaftswerte in der TableView an.
        this.columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        // CellFactory schreibt Eigenschaftswerte in die TableView
        this.columnFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.columnSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        this.columnPhoneNumber.setCellFactory(TextFieldTableCell.forTableColumn());

        ChangeListener<String> checkTableInteger = (observableValue, oldText, newText) -> {

        };

        this.columnPhoneNumber.textProperty().addListener(checkTableInteger);

        // Fügt die Daten in die TableView ein
        this.tableView.setItems(this.caregivers);

        this.buttonDelete.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldCaregiver, newCaregiver) -> AllCaregiverController.this.buttonDelete.setDisable(newCaregiver == null));

        this.buttonAdd.setDisable(true);
        ChangeListener<String> inputNewCaregiverListener = (observableValue, oldText, newText) -> AllCaregiverController.this.buttonAdd.setDisable(!AllCaregiverController.this.areInputDataValid());
        this.textFieldSurname.textProperty().addListener(inputNewCaregiverListener);
        this.textFieldFirstName.textProperty().addListener(inputNewCaregiverListener);
        this.textFieldPhoneNumber.textProperty().addListener(inputNewCaregiverListener);
        ChangeListener<String> checkInputInteger = (observableValue, oldText, newText) -> {
            if (!newText.matches("\\d*")) {
                textFieldPhoneNumber.setText(newText.replaceAll("[^\\d]", ""));
            }
        };
        this.textFieldPhoneNumber.textProperty().addListener(checkInputInteger);

    }

    /**
     * Falls eine Zelle der Spalte mit den Vornamen geändert wurde, wird diese Methode aufgerufen, um die Änderung zu speichern.
     *
     * @param event Ereignis mit dem geänderten Objekt und der Änderung.
     */
    @FXML
    public void handleOnEditFirstname(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setFirstName(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * Falls eine Zelle der Spalte mit den Nachnamen geändert wurde, wird diese Methode aufgerufen, um die Änderung zu speichern.
     *
     * @param event Ereignis mit dem geänderten Objekt und der Änderung.
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setSurname(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * Updated einen Angestellten, indem die Methode <code>update()</code> von {@link CaregiverDao} aufgerufen wird.
     *
     * @param event Ereignis mit dem geänderten Objekt und der Änderung.
     */
    private void doUpdate(TableColumn.CellEditEvent<Caregiver, String> event) {
        try {
            this.dao.update(event.getRowValue());
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
    }

    /**
     * Schreibt alle Angestellten in die Tabelle, indem die Liste aller Angestellten gelöscht und erneut mit allen gespeicherten
     * Angestellten, geliefert von {@link CaregiverDao}, gefüllt wird.
     */
    private void readAllAndShowInTableView() {
        this.dao = DaoFactory.getDaoFactory().createCaregiverDAO();
        try {
            this.caregivers.clear();
            this.caregivers.addAll(this.dao.readAll());
        } catch (SQLException exception) {
            System.setErr(System.err);
        }
    }

    /**
     * Diese Methode behandelt Ereignisse, die vom Button zum Löschen von Angestellten ausgelöst werden. Sie ruft {@link CaregiverDao} auf, um den
     * Angestellten aus der Datenbank zu löschen und entfernt das Objekt aus der Liste, die die Datenquelle der
     * <code>TableView</code> ist.
     */
    @FXML
    public void handleDeleteOrLock() {
        Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                this.dao.updateLockStatus(selectedItem.getCid(), true);
            } catch (SQLException exception) {
                System.setErr(System.err);
            }
            readAllAndShowInTableView();
        }
    }


    /**
     * Diese Methode behandelt die Ereignisse, die durch den Button zum Hinzufügen eines Angestellten ausgelöst werden. Sie sammelt die Daten von den
     * <code>TextField</code>s, erstellt ein Objekt der Klasse <code>Caregiver</code> und übergibt das Objekt an {@link CaregiverDao}, um die Daten zu speichern.
     */
    @FXML
    public void handleAdd() {
        String surname = this.textFieldSurname.getText();
        String firstName = this.textFieldFirstName.getText();
        String phoneNumber = this.textFieldPhoneNumber.getText();
        boolean locked = false;
        try {
            this.dao.create(new Caregiver(firstName, surname, phoneNumber, locked));
        } catch (SQLException exception) {
            System.setErr(System.err);
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
        this.textFieldPhoneNumber.clear();
    }

    private boolean areInputDataValid() {
        return !this.textFieldFirstName.getText().isBlank() && !this.textFieldSurname.getText().isBlank() && !this.textFieldPhoneNumber.getText().isBlank();
    }

    @FXML
    public void handleOnEditPhoneNumber(TableColumn.CellEditEvent<Caregiver, String> event) {
        String newValue = event.getNewValue();
        if (!newValue.matches("\\d*")) {
            event.getRowValue().setPhoneNumber(newValue.replaceAll("[^\\d]", ""));
            columnPhoneNumber.setText(newValue.replaceAll("[^\\d]", ""));
            this.doUpdate(event);
        }
    }
}
