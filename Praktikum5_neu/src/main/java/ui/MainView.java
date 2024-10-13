package ui;

import bank.PrivateBank;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransferPaymentAttributeException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

//Interface Initializable führt die Methode initialize ein und ist für FMXL Controller gedacht
public class MainView implements Initializable
{
    //Liste für Account Namen
    @FXML
    private ListView<String> accountListView;
    public AnchorPane anchor;
    //Instanz von PrivateBank
    private static PrivateBank p1;
    //speichert ausgewähltes Element(Account)
    public static String selected;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //versucht ein Objekt von PrivateBank zu erstellen
        try
        {
            p1 = new PrivateBank("Bank 3", 0.1, 0.15, "bank3");
        } catch (TransferPaymentAttributeException | IOException exc1)
        {
            System.out.println("Error");
        }

        //speichert die Accounts der Bank in einer observable List
        accountListView.setItems(FXCollections.observableArrayList(p1.getAllAccounts()));
    }

    public void showAccount(ActionEvent actionEvent) throws IOException
    {
        //speichert Namen vom ausgewählten Account
        selected = accountListView.getSelectionModel().getSelectedItem();

        //lädt AccountView mit Hilfe des FMXLLoaders
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AccountView.fxml"));
        //speichert AccountView in root
        Parent root = fxmlLoader.load();

        //setzt root als neue die neue Stage
        FxApplication.stage1.setScene(new Scene(root));
    }

    public void deleteAccount(ActionEvent actionEvent)
    {
        //erstellt Dialog Fenster zum Bestätigen des Löschvorgangs
        //Dialog erwartet eine Bestätigung vom Nutzer
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Account löschen");
        alert.setHeaderText("Achtung!");
        alert.setContentText("Wollen Sie diesen Account wirklich löschen?");

        //Zeigt Dialog Fenster an und wartet auf Benutzer Eingabe
        Optional<ButtonType> result = alert.showAndWait();

        //wird nach Benutzer Eingabe(OK oder abbrechen) aufgerufen
        if (result.get() == ButtonType.OK)
        {
            //versucht Account zu löschen wenn OK gedrückt wurde
            try
            {
                p1.deleteAccount(accountListView.getSelectionModel().getSelectedItem());
            } catch (AccountDoesNotExistException e)
            {
                alert("Der Account existiert nicht!");
            } catch (IOException e)
            {
                alert("Eingabe inkorrekt!");
            }
        } else
        {
            //bricht Vorgang ab wenn abbrechen ausgewählt wurde
            return;
        }

        //aktualisiert die Liste der Accounts
        accountListView.setItems(FXCollections.observableArrayList(p1.getAllAccounts()));
    }

    public void addAccount(ActionEvent actionEvent)
    {
        //erstellt Dialog Fenster zum Account Erstellen
        TextInputDialog dialog = new TextInputDialog();
        //blockiert Interaktion mit dem Hauptfenster bis der Dialog (ab-)geschlossen ist
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Account hinzufügen");
        dialog.setHeaderText("Account hinzufügen");
        dialog.setContentText("Accountname");

        //Zeigt Dialog Fenster an und wartet auf Benutzer Eingabe
        Optional<String> result = dialog.showAndWait();

        //wenn eine Eingabe gefunden wurde
        if (result.isPresent())
        {
            //Error wenn Namensfeld leer ist
            if (result.get().equals(""))
            {
                Alert missing = new Alert(Alert.AlertType.INFORMATION);
                missing.setContentText("Der Name darf nicht leer sein!");
                missing.show();
                return;
            }

            //versucht neuen Account zu erstellen
            try
            {
                p1.createAccount(result.get());
            } catch (AccountAlreadyExistsException e)
            {
                alert("Der Account existiert bereits!");
            }
        }

        //aktualisiert die Liste der Accounts
        accountListView.setItems(FXCollections.observableArrayList(p1.getAllAccounts()));
    }

    //erzeugt ein Dialog Fenster zur Ausgabe von Fehlern
    public static void alert(String text)
    {
        //erzeugt einen informativen Dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //setzt den Text auf den übergebenen Wert
        alert.setHeaderText(text);
        //macht das Dialog Fenster sichtbar
        alert.show();
    }

    public static String getSelected()
    {
        return selected;
    }

    public static PrivateBank getBank()
    {
        return p1;
    }
}
