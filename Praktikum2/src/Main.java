import bank.*;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
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

        Transfer transfer2 = new Transfer("26.10.2023", 300.0, "Test Transfer 2", "Sender A", "Recipient B");
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



        // Validierungsfehler testen
        Payment invalidPayment = new Payment("27.10.2023", -10.0, "Invalid Payment", 10, -1);
        Transfer invalidTransfer = new Transfer("28.10.2023", -50.0, "Invalid Transfer");



    }
}