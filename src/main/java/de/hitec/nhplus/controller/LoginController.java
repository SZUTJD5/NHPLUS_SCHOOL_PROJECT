package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.datastorage.ConnectionBuilder;
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

public class LoginController {
    public TextField textFieldLoginName;
    public TextField textFieldPassword;
    public BorderPane loginBorderPane;
    public Button buttonLogin;
    public int loggedInCaregiver;

    private static void alert(Exception e, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    @FXML
    private void handleLogin() {
        final String loginName = textFieldLoginName.getText();
        final String password = textFieldPassword.getText();

        if (loginName.isEmpty() || password.isEmpty()) {
            alert(new Exception(), "Nutzername oder Password fehlt!", "Bitte gebe einen Benutzernamen und Password ein!");
            return;
        }

        final String SQL = "SELECT login_ID FROM logins WHERE name = ? AND password = ?";
        try (Connection connection = ConnectionBuilder.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            if (connection.isClosed()) {
                alert(new SQLException("Failed to establish a database connection."), "Fehler!", "Konnte keine Verbindung zur Datenbank aufbauen!");
            }

            preparedStatement.setString(1, loginName);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    loggedInCaregiver = resultSet.getInt("login_ID");
                    System.out.println(loggedInCaregiver);
                    // Proceed with the successful login process, e.g., load the main window
                    loadMainWindow();
                } else {
                    alert(new Exception(), "Login fehlgeschlagen", "Ungültiger Nutzername oder Passwort!");
                    ConnectionBuilder.closeConnection();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            alert(e, "Datenbankfehler", "Ein Fehler ist bei der Datenbankabfrage aufgetreten.");
        }
    }

    private void loadMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/MainWindowView.fxml"));
            BorderPane mainBorderPane = loader.load();

            // Get the stage and adjust its size to fit the content
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