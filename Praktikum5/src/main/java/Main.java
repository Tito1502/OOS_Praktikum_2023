import bank.*;
import bank.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws AccountAlreadyExistsException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, TransactionDoesNotExistException, TransferPaymentAttributeException, IOException {
        // Testen der Payment-Klasse
        System.out.println("Informationen zu den Zahlungen:");
        Payment payment1 = new Payment("23.10.2023", 100.0, "Test Payment 1");
        System.out.println(payment1);
        System.out.println("-------------------------------");

        Payment payment2 = new Payment("24.10.2023", 50.0, "Test Payment 2", 0.1, 0.2);
        System.out.println(payment2);
        System.out.println("-------------------------------");

        Payment payment3 = new Payment(payment2); // Kopieren von payment2
        System.out.println(payment3);
        System.out.println(payment3.equals(payment2));
        System.out.println("-------------------------------");

        payment3.setAmount(60.0);
        System.out.println(payment2);
        System.out.println(payment3);
        System.out.println(payment3.equals(payment2));
        System.out.println("-------------------------------");

        Payment payment4 = new Payment("24.10.2023", -50.0, "Test Payment 2", 0.1, 0.5);
        System.out.println(payment4);
        System.out.println("-------------------------------");





        System.out.println("\n\nInformationen zu den Zahlungen:");
        // Testen der Transfer-Klasse
        Transfer transfer1 = new Transfer("25.10.2023", 200.0, "Test Transfer 1");
        System.out.println(transfer1);
        System.out.println("-------------------------------");

        Transfer transfer2 = new Transfer("26.10.2023", 300.0, "Test Transfer 2", "user1", "user2");
        System.out.println(transfer2);
        System.out.println("-------------------------------");

        Transfer transfer3 = new Transfer(transfer2); // Kopieren von transfer2
        System.out.println(transfer3);
        System.out.println(transfer3.equals(transfer2));
        System.out.println("-------------------------------");

        transfer3.setAmount(60.0);
        System.out.println(transfer2);
        System.out.println(transfer3);
        System.out.println(transfer3.equals(transfer2));
        System.out.println("\n\n");






        PrivateBank bank1 = new PrivateBank("Bank1", 0.1, 0.5,"bank1");
        PrivateBank bank2 = new PrivateBank("Bank2", 0.2, 0.8,"bank2");
        try {
            //bank1.createAccount("Account1");


            OutgoingTransfer transfer10 = new OutgoingTransfer("26.10.2023", 2500, "Test OutgoingTransfer 1", "Account2", "Account1");
            OutgoingTransfer transfer11 = new OutgoingTransfer("26.10.2023", 220.0, "Test OutgoingTransfer 2", "Account2", "Account1");
            IncomingTransfer transfer12 = new IncomingTransfer("26.10.2023", 450.0, "Test IncomingTransfer 3", "Account1", "Account2");
            Payment payment10 = new Payment("24.10.2023", -50.0, "Test Payment 1", 0.5, 0.8);
            Payment payment11 = new Payment("24.10.2023", 150.0, "Test Payment 2", 0.5, 0.8);
/*
            bank1.addTransaction("Account1",transfer10);
            bank1.addTransaction("Account1",transfer11);
            bank1.addTransaction("Account1",transfer12);
            bank1.addTransaction("Account1",payment10);
            bank1.addTransaction("Account1",payment11);


            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transfer10);
            transactions.add(transfer11);
            transactions.add(transfer12);
            transactions.add(payment10);


            bank1.createAccount("Account2",transactions);*/

            System.out.println(bank1.getAccountBalance("Account1"));
            System.out.println(bank1.getAccountBalance("Account2"));

            System.out.println("-------------------------------");

            System.out.println(bank1.getTransactions("Account1"));
            System.out.println(bank1.getTransactions("Account2"));

            System.out.println("-------------------------------");

            System.out.println(bank1.getTransactionsSorted("Account1", true));
            System.out.println(bank1.getTransactionsSorted("Account1", false));

            System.out.println("-------------------------------");

            System.out.println(bank1.getTransactionsSorted("Account2", true));
            System.out.println(bank1.getTransactionsSorted("Account2", false));

            System.out.println("-------------------------------");

            System.out.println(bank1.getTransactionsByType("Account1", true));
            System.out.println(bank1.getTransactionsByType("Account1", false));

            System.out.println("-------------------------------");

            System.out.println(bank1.getTransactionsByType("Account2", true));
            System.out.println(bank1.getTransactionsByType("Account2", false));

            System.out.println("-------------------------------");

            System.out.println(bank1.containsTransaction("Account2", payment10));
            System.out.println(bank1.containsTransaction("Account2", payment11));

            System.out.println("-------------------------------");

            System.out.println(bank1.equals(bank2));

            System.out.println("-------------------------------");

            System.out.println(bank1);
            System.out.println(bank2);


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PrivateBank bank = new PrivateBank("Bank1", 2, 0.5,"bank3");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PrivateBank bank = new PrivateBank("Bank1", 0.5, 2,"bank4");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Testen der PrivateBankAlt-Klasse
        PrivateBankAlt bankAlt = new PrivateBankAlt("BankAlt", 0.8, 0.9);
        PrivateBankAlt bankAlt2 = new PrivateBankAlt("BankAlt", 0.4, 0.5);
        try {
            bankAlt.createAccount("Account1");


            OutgoingTransfer transfer10 = new OutgoingTransfer("26.10.2023", 2500, "Test Transfer 1", "Account2", "Account1");
            OutgoingTransfer transfer11 = new OutgoingTransfer("26.10.2023", 220.0, "Test Transfer 2", "Account2", "Account1");
            IncomingTransfer transfer12 = new IncomingTransfer("26.10.2023", 450.0, "Test Transfer 3", "Account1", "Account2");
            Payment payment10 = new Payment("24.10.2023", -50.0, "Test Payment 1", 0.5, 0.8);
            Payment payment11 = new Payment("24.10.2023", 150.0, "Test Payment 2", 0.5, 0.8);

            bankAlt.addTransaction("Account1",transfer10);
            bankAlt.addTransaction("Account1",transfer11);
            bankAlt.addTransaction("Account1",transfer12);
            bankAlt.addTransaction("Account1",payment10);
            bankAlt.addTransaction("Account1",payment11);


            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transfer10);
            transactions.add(transfer11);
            transactions.add(transfer12);
            transactions.add(payment10);


            bankAlt.createAccount("Account2",transactions);

            System.out.println(bankAlt.getAccountBalance("Account1"));
            System.out.println(bankAlt.getAccountBalance("Account2"));

            System.out.println("-------------------------------");

            System.out.println(bankAlt.getTransactions("Account1"));
            System.out.println(bankAlt.getTransactions("Account2"));

            System.out.println("-------------------------------");

            System.out.println(bankAlt.getTransactionsSorted("Account1", true));
            System.out.println(bankAlt.getTransactionsSorted("Account1", false));

            System.out.println("-------------------------------");

            System.out.println(bankAlt.getTransactionsSorted("Account2", true));
            System.out.println(bankAlt.getTransactionsSorted("Account2", false));

            System.out.println("-------------------------------");

            System.out.println(bankAlt.getTransactionsByType("Account1", true));
            System.out.println(bankAlt.getTransactionsByType("Account1", false));

            System.out.println("-------------------------------");

            System.out.println(bankAlt.getTransactionsByType("Account2", true));
            System.out.println(bankAlt.getTransactionsByType("Account2", false));

            System.out.println("-------------------------------");

            System.out.println(bankAlt.containsTransaction("Account2", payment10));
            System.out.println(bankAlt.containsTransaction("Account2", payment11));

            System.out.println("-------------------------------");

            System.out.println(bankAlt.equals(bankAlt2));

            System.out.println("-------------------------------");

            System.out.println(bankAlt);
            System.out.println(bankAlt2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Validierungsfehler testen
        try {
            Payment invalidPayment = new Payment("27.10.2023", -10.0, "Invalid Payment", 10, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Transfer invalidTransfer = new Transfer("28.10.2023", -50.0, "Invalid Transfer");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}