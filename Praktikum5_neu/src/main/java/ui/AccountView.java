package ui;

import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.Payment;
import bank.Transaction;
import bank.exceptions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

//Interface Initializable führt die Methode initialize ein und ist für FMXL Controller gedacht
public class AccountView implements Initializable
{
    //Label für Account Name
    public Label name;
    //Label für Kontostand
    public Label balance;
    public AnchorPane anchor;
    //Liste für Transaktionen
    public ListView transactionListView;
    //Sortierung standardmäßig aus
    public int transactionCase = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //setzt den Titel auf den Account Namen
        name.setText(MainView.getSelected());

        //versucht den Kontostand zu ermitteln und zu setzen
        //String.valueOf wandelt den Wert der Rückgabe in einen String um
        balance.setText(String.valueOf(MainView.getBank().getAccountBalance(MainView.getSelected())));

        //fügt alle Transaktionen des Accounts in transactionListView hinzu
        transactionListView.getItems().addAll(MainView.getBank().getTransactions(MainView.getSelected()));
    }

    //verlässt AccountView
    public void goBack(ActionEvent actionEvent) throws IOException
    {
        //lädt MainView und speichert es in root
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent root = fxmlLoader.load();

        //setzt root als die neue Stage
        FxApplication.stage1.setScene(new Scene(root));
    }

    public void deleteTransaction(ActionEvent actionEvent)
    {
        //zeigt einen Dialog zum Löschen einer Transaktion an
        //Dialog erwartet eine Bestätigung vom Nutzer
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Transaktion löschen");
        alert.setHeaderText("Achtung!");
        alert.setContentText("Transaktion wirklich löschen?");

        //Zeigt Dialog Fenster an und wartet auf Benutzer Eingabe
        Optional<ButtonType> result = alert.showAndWait();

        //versucht Transaktion zu löschen
        try
        {
            MainView.getBank().removeTransaction(MainView.getSelected(), (Transaction) transactionListView.getSelectionModel().getSelectedItem());
        } catch (AccountDoesNotExistException | TransactionDoesNotExistException e)
        {
            throw new RuntimeException(e);
        }

        //aktualisiert die Transaktionsliste mit updateListView()
        updateListView();
    }

    public void addTransaction(ActionEvent actionEvent)
    {
        //Liste zur Auswahl der Transaktions Art
        List<String> choices = new ArrayList<>();
        choices.add("Payment");
        choices.add("Transfer");

        //erzeugt ein Dialog Feld zur Auswahl der Transaktions Art, wobei Payment standardmäßig ausgweählt ist
        ChoiceDialog<String> dialogChoice = new ChoiceDialog<>("Payment", choices);
        dialogChoice.setTitle("Neue Transaktion hinzufügen");
        dialogChoice.setHeaderText("Neue Transaktion hinzufügen");
        dialogChoice.setContentText("Wählen Sie den Transaktionenart");

        //Zeigt Dialog Fenster an und wartet auf Benutzer Eingabe
        Optional<String> resultOfChoice = dialogChoice.showAndWait();

        //wenn der Nutzer tatsächlich etwas ausgewählt hat
        if (resultOfChoice.isPresent())
        {
            //erstellt einen Dialog zum Erstellen einer neuen Transaktion
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Neue Transaktion hinzufügen");
            dialog.setHeaderText("Transaktion hinzufügen");

            //erstellt ein Grid zur Formatierung
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            //fügt Textfelder für die Eingabe der 3 Standard Attribute hinzu
            TextField date = new TextField();
            date.setPromptText("Datum");
            TextField amount = new TextField();
            amount.setPromptText("Menge");
            TextField description = new TextField();
            description.setPromptText("Beschreibung");

            //fügt die Eingabefelder in das Grid ein
            grid.add(new Label("Datum:"), 0, 0);
            grid.add(date, 0, 0);
            grid.add(new Label("Menge:"), 0, 1);
            grid.add(amount, 0, 1);
            grid.add(new Label("Beschreibung:"), 0, 2);
            grid.add(description, 0, 2);

            //wenn Transaktions Art Payment ausgewählt wurde
            if (resultOfChoice.get().equals("Payment"))
            {
                //erstellt Textfelder für die Eingabe der Payment spezifischen Attribute
                TextField incoming = new TextField();
                incoming.setPromptText("IncomingInterest");
                TextField outgoing = new TextField();
                outgoing.setPromptText("OutgoingInterest");

                //fügt die Eingabefelder in das Grid ein
                grid.add(new Label("IncomingInterest:"), 0, 3);
                grid.add(incoming, 0, 3);
                grid.add(new Label("OutgoingInterest:"), 0, 4);
                grid.add(outgoing, 0, 4);

                //setzt den Inhalt vom nächsten Dialog
                dialog.getDialogPane().setContent(grid);
                //schließt den Auswahl Dialog
                dialogChoice.close();
                //wartet bis der Erstell Dialog abgeschlossen wurde
                dialog.showAndWait().ifPresent(result -> {

                    //bricht den Vorgang mit einem Error ab, wenn eine der Eingaben leer ist
                    if (Objects.equals(date.getText(), "") ||
                            Objects.equals(amount.getText(), "") ||
                            Objects.equals(description.getText(), "") ||
                            Objects.equals(incoming.getText(), "") ||
                            Objects.equals(outgoing.getText(), ""))
                    {
                        Alert missing = new Alert(Alert.AlertType.WARNING);
                        missing.setContentText("Transaktion unvollständig!");
                        missing.show();
                        return;
                    }

                    //versucht neues Payment Objekt hinzuzufügen
                    try
                    {
                        MainView.getBank().addTransaction(MainView.getSelected(), new Payment(date.getText(), Double.parseDouble(amount.getText()), description.getText(), Double.parseDouble(incoming.getText()), Double.parseDouble(outgoing.getText())));
                    } catch (TransactionAlreadyExistException | AccountDoesNotExistException |
                             TransferPaymentAttributeException | IOException | TransactionAttributeException e)
                    {
                        throw new RuntimeException(e);
                    }
                });
            //wenn Trasnaktions Art Transfer ausgewählt wurde bzw nicht Payment
            } else
            {
                //erstellt Textfelder für die Eingabe der Transfer spezifischen Attribute
                TextField sender = new TextField();
                sender.setPromptText("Sender");
                TextField recipient = new TextField();
                recipient.setPromptText("Empfänger");

                //fügt die Eingabefelder in das Grid ein
                grid.add(new Label("Sender:"), 0, 3);
                grid.add(sender, 0, 3);
                grid.add(new Label("Empfänger:"), 0, 4);
                grid.add(recipient, 0, 4);

                //setzt den Inhalt vom nächsten Dialog
                dialog.getDialogPane().setContent(grid);
                //schließt den Auswahl Dialog
                dialogChoice.close();
                //wartet bis der Erstell Dialog abgeschlossen wurde
                dialog.showAndWait().ifPresent(result -> {

                    //bricht den Vorgang mit einem Error ab, wenn eine der Eingaben leer ist
                    if (Objects.equals(date.getText(), "") ||
                            Objects.equals(amount.getText(), "") ||
                            Objects.equals(description.getText(), "") ||
                            Objects.equals(sender.getText(), "") ||
                            Objects.equals(recipient.getText(), ""))
                    {
                        Alert missing = new Alert(Alert.AlertType.WARNING);
                        missing.setContentText("Transaktion unvollständig!");
                        missing.show();
                        return;
                    }


                    //wenn Sender = Account Name dann OutgoingTransfer
                    if (sender.getText().equals(MainView.getSelected()))
                    {
                        try
                        {
                            MainView.getBank().addTransaction(MainView.getSelected(), new OutgoingTransfer(date.getText(), Double.parseDouble(amount.getText()), description.getText(), sender.getText(), recipient.getText()));
                        } catch (TransactionAlreadyExistException | AccountDoesNotExistException |
                                 TransferPaymentAttributeException | IOException | TransactionAttributeException e)
                        {
                            throw new RuntimeException(e);
                        }
                        //wenn Sender =/= Account Name dann OutgoingTransfer
                    } else
                    {
                        //versucht neues IncomingTransfer Objekt zu erstellen
                        try
                        {
                            MainView.getBank().addTransaction(MainView.getSelected(), new IncomingTransfer(date.getText(), Double.parseDouble(amount.getText()), description.getText(), sender.getText(), recipient.getText()));
                        } catch (TransactionAlreadyExistException | AccountDoesNotExistException |
                                 TransferPaymentAttributeException | IOException | TransactionAttributeException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }

            //aktualisiert die Transaktionsliste mit updateListView()
            updateListView();
        }
    }

    public void updateListView()
    {
        //versucht Kontostand zu ermitteln und zu setzen
        balance.setText(String.valueOf(MainView.getBank().getAccountBalance(MainView.getSelected())));

        //ändert die Sortierung der Transaktionen entsprechend der ausgewählten Sortier Art
        switch (transactionCase)
        {
            case (-1):
                transactionListView.getItems().clear();
                transactionListView.getItems().addAll(MainView.getBank().getTransactions(MainView.getSelected()));
                return;
            case (0):
                transactionListView.getItems().clear();
                transactionListView.getItems().addAll(MainView.getBank().getTransactionsSorted(MainView.getSelected(), true));
                return;
            case (1):
                transactionListView.getItems().clear();
                transactionListView.getItems().addAll(MainView.getBank().getTransactionsSorted(MainView.getSelected(), false));
                return;
            case (2):
                transactionListView.getItems().clear();
                transactionListView.getItems().addAll(MainView.getBank().getTransactionsByType(MainView.getSelected(), true));
                return;
            case (3):
                transactionListView.getItems().clear();
                transactionListView.getItems().addAll(MainView.getBank().getTransactionsByType(MainView.getSelected(), false));
                return;
        }
    }

    //sortiert die Transaktionen aufsteigend nach dem Betrag
    public void ascSort(ActionEvent actionEvent)
    {
        //löscht den Inhalt der Transaktions Liste
        transactionListView.getItems().clear();
        //füllt die Liste mit Transaktionen aufsteigend nach dem Betrag
        transactionListView.getItems().addAll(MainView.getBank().getTransactionsSorted(MainView.getSelected(), true));
        //setzt die Sortierung auf Aufsteigend
        transactionCase = 0;
    }

    //sortiert die Transaktionen absteigend nach dem Betrag
    public void descSort(ActionEvent actionEvent)
    {
        //löscht den Inhalt der Transaktions Liste
        transactionListView.getItems().clear();
        //füllt die Liste mit Transaktionen absteigend nach dem Betrag
        transactionListView.getItems().addAll(MainView.getBank().getTransactionsSorted(MainView.getSelected(), false));
        //setzt die Sortierung auf Absteigend
        transactionCase = 1;
    }

    //sortiert die Transaktionen so dass nur welche mit positivem Betrag angezeigt werden
    public void positiveSort(ActionEvent actionEvent)
    {
        //löscht den Inhalt der Transaktions Liste
        transactionListView.getItems().clear();
        //füllt die Liste mit allen positiven Transaktionen
        transactionListView.getItems().addAll(MainView.getBank().getTransactionsByType(MainView.getSelected(), true));
        //setzt die Sortierung auf positiv
        transactionCase = 2;
    }

    //sortiert die Transaktionen so dass nur welche mit negativem Betrag angezeigt werden
    public void negativeSort(ActionEvent actionEvent)
    {
        //löscht den Inhalt der Transaktions Liste
        transactionListView.getItems().clear();
        //füllt die Liste mit allen negativen Transaktionen
        transactionListView.getItems().addAll(MainView.getBank().getTransactionsByType(MainView.getSelected(), false));
        //setzt die Sortierung auf negativ
        transactionCase = 3;
    }

}
