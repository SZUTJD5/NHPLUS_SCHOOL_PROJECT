// Paketanweisung und Importe

package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.datastorage.ConnectionBuilder;
import de.hitec.nhplus.model.ActiveAcount;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Der <code>LoginController</code> verwaltet den Anmeldevorgang der Anwendung.
 * Er überprüft die Eingaben der Benutzer und leitet sie bei erfolgreicher Anmeldung zur Hauptansicht weiter.
 */
public class LoginController {
    // Textfelder für die Eingabe des Benutzernamens und des Passworts
    public TextField textFieldLoginName;
    public TextField textFieldPassword;
    // BorderPane des Login-Fensters
    public BorderPane loginBorderPane;
    // Anmeldebutton
    public Button buttonLogin;

    /**
     * Zeigt einen Fehler-Dialog an.
     *
     * @param e       Die Ausnahme, die den Fehler verursacht hat.
     * @param title   Der Titel des Dialogs.
     * @param message Die Nachricht des Dialogs.
     */
    private static void alert(Exception e, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    /**
     * Ruft das in der Datenbank gespeicherte Passwort für einen gegebenen Login-Namen ab.
     *
     * @param loginName Der Login-Name des Benutzers.
     * @return Das gehashte Passwort des Benutzers.
     */
    private static String getPassword(String loginName) {
        String hashedPassword = null;
        final String passwordSQL = "SELECT password FROM logins WHERE name = ?";
        try (Connection connection = ConnectionBuilder.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(passwordSQL)) {
            preparedStatement.setString(1, loginName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    hashedPassword = resultSet.getString("password");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hashedPassword;
    }

    /**
     * Behandelt den Anmeldevorgang.
     * Überprüft die Eingaben des Benutzers und vergleicht das Passwort mit dem in der Datenbank gespeicherten Hash.
     */
    @FXML
    private void handleLogin() {
        final String loginName = textFieldLoginName.getText();
        final String password = textFieldPassword.getText();

        if (loginName.isEmpty() || password.isEmpty()) {
            alert(new Exception(), "Nutzername oder Password fehlt!", "Bitte geben Sie einen Benutzernamen und Password ein!");
            return;
        }

        // In Datenbank gespeichertes Passwort über login Namen erhalten
        String hashedPassword = getPassword(loginName);
        PasswordHashingController passwordHashingController = new PasswordHashingController();

        // Bezieht die Login_ID um nach erfolgreicher anmeldung den Nutzer festzulegen
        ConnectionBuilder.closeConnection();
        final String SQL = "SELECT login_ID FROM logins WHERE name = ?";
        try (Connection connection = ConnectionBuilder.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            // Falls das Passwort korrekt ist, wird der Login vorgang fortgeführt.
            if (hashedPassword != null && passwordHashingController.verifyPassword(password, hashedPassword)) {
                if (connection.isClosed()) {
                    alert(new SQLException("Failed to establish a database connection."), "Fehler!", "Konnte keine Verbindung zur Datenbank aufbauen!");
                }

                preparedStatement.setString(1, loginName);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Erstellt ein Singleton für die Verwendung im NewTreatmentController
                        ActiveAcount activeAccount = ActiveAcount.getInstance(loginName);
                        System.out.println("Successfully logged in: " + activeAccount.getFirstName() + " " + activeAccount.getSurname() + "!");
                        // Fortfahren mit dem erfolgreichen Anmeldevorgang, z.B. das Hauptfenster laden
                        loadMainWindow();
                    } else {
                        alert(new Exception(), "Login fehlgeschlagen", "Ungültiger Nutzername oder Passwort!");
                        ConnectionBuilder.closeConnection();
                    }
                }
            } else {
                ConnectionBuilder.closeConnection();
                alert(new Exception(), "Falscher Benutzername oder Passwort!", "Überprüfen Sie bitte ihre Eingaben");
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            alert(e, "Datenbankfehler", "Es ist ein Fehler bei der Datenbankabfrage aufgetreten.");
        }
    }

    /**
     * Lädt das Hauptfenster der Anwendung nach erfolgreicher Anmeldung.
     */
    private void loadMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/MainWindowView.fxml"));
            BorderPane mainBorderPane = loader.load();

            // Holt die aktuelle Bühne und passt ihre Größe an den Inhalt an
            Stage stage = (Stage) loginBorderPane.getScene().getWindow();
            Scene scene = new Scene(mainBorderPane);
            stage.setScene(scene);
            stage.sizeToScene();
            ConnectionBuilder.closeConnection();
        } catch (IOException e) {
            alert(e, "Datenbankfehler", "Ein Fehler ist beim Laden des Hauptfensters aufgetreten.");
        }
    }
}