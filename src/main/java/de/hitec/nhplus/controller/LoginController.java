package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    // Feld zum eintragen des Nutzernamen
    public TextField textFieldLoginName;
    // Feld zum eintragen des Passworts
    public TextField textFieldPassword;
    public BorderPane loginBorderPane;
    public Button buttonLogin;
    // Button zum Login

    /*
    @FXML
    private void handleLogin(ActionEvent event) {
        if (textFieldLoginName.getText().equals(loginName) && textFieldPassword.getText().equals(password)) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/MainWindowView.fxml"));
            try {
                loginBorderPane.setCenter(loader.load());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

     */

    @FXML
    private void handleLogin() {
        String password = "adminadmin";
        String loginName = "Jhon";
        if (textFieldLoginName.getText().equals(loginName) && textFieldPassword.getText().equals(password)) {
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/MainWindowView.fxml"));
                BorderPane mainBorderPane = loader.load();

                // Get the stage and adjust its size to fit the content
                Stage stage = (Stage) loginBorderPane.getScene().getWindow();
                Scene scene = new Scene(mainBorderPane);
                stage.setScene(scene);
                stage.sizeToScene();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }


}
