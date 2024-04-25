// Paketanweisung und Importe
package de.hitec.nhplus;

import de.hitec.nhplus.datastorage.ConnectionBuilder;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

// Definition der Main-Klasse, die von Application erbt
public class Main extends Application {

    // Deklaration der primaryStage-Variable vom Typ Stage
    private Stage primaryStage;

    // Startmethode der Anwendung, setzen des primaryStage und Aufruf der mainWindow-Methode
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainWindow();
    }

    // Definition der mainWindow-Methode
    public void mainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/MainWindowView.fxml")); // Erstellen eines FXMLLoaders, um die Benutzeroberfläche zu laden
            BorderPane pane = loader.load(); // Laden des BorderPanes aus der FXML-Datei

            Scene scene = new Scene(pane); // Erstellen einer neuen Szene mit dem BorderPane
            this.primaryStage.setTitle("NHPlus");  // Setzen des Fenstertitels
            this.primaryStage.setScene(scene); // Setzen der Szene für die primaryStage
            this.primaryStage.setResizable(false); // Festlegen, dass das Fenster nicht veränderbar ist
            this.primaryStage.show(); // Anzeigen des Fensters

            // Ereignisbehandlung für das Schließen des Fensters
            this.primaryStage.setOnCloseRequest(event -> {
                ConnectionBuilder.closeConnection(); // Schließen der Verbindung
                Platform.exit(); // Beenden der Platform (JavaFX)
                System.exit(0); // Beenden des Systems
            });
            // Catch-Block für IOException
        } catch (IOException exception) {
            exception.printStackTrace(); // Ausgabe der Fehlermeldung
        }
    }

    // Hauptmethode, die beim Start der Anwendung aufgerufen wird
    public static void main(String[] args) {
        launch(args);
    } // Starten der JavaFX-Anwendung
}