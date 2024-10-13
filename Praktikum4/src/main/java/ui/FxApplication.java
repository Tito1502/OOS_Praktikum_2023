package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class FxApplication extends Application
{
    //Loader für die fmxl Dateien
    private FXMLLoader fxmlLoader;

    //Fenster in dem die Anwendung läuft
    public static Stage stage1;

    //wird beim Starten aufgerufen
    @Override
    public void start(Stage stage) throws Exception
    {
        //lädt die MainView mit Hilfe des FXMLLoaders
        fxmlLoader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        //speichert MainView in root
        Parent root = fxmlLoader.load();
        //übernimmt die übergebene Stage
        stage1 = stage;
        //setzt root als die neue Stage
        stage.setScene(new Scene(root));
        //macht die Stage sichtbar
        stage.show();
    }

    //started die JavaFX Anwendung
    public static void main(String[] args)
    {
        //Initialisiert die Anwendung, erstellt eine Instanz und ruft "start" auf
        launch(args);
    }
}
