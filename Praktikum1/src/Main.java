import bank.*;

public class Main {
    public static void main(String[] args) {
        // Testen der Payment-Klasse
        Payment payment1 = new Payment("23.10.2023", 100.0, "Test Payment 1");
        Payment payment2 = new Payment("24.10.2023", 50.0, "Test Payment 2", 0.1, 0.2);
        Payment payment3 = new Payment(payment2); // Kopieren von payment2
        

        // Testen der Transfer-Klasse
        Transfer transfer1 = new Transfer("25.10.2023", 200.0, "Test Transfer 1");
        Transfer transfer2 = new Transfer("26.10.2023", 300.0, "Test Transfer 2", "Sender A", "Recipient B");
        Transfer transfer3 = new Transfer(transfer2); // Kopieren von transfer2

        // Validierungsfehler testen
        Payment invalidPayment = new Payment("27.10.2023", -10.0, "Invalid Payment", 10, -1);
        Transfer invalidTransfer = new Transfer("28.10.2023", -50.0, "Invalid Transfer");

        // Ausgabe der Informationen
        System.out.println("Informationen zu den Zahlungen:");
        payment1.printObject();
        System.out.println("-------------------------------");
        payment2.printObject();
        System.out.println("-------------------------------");
        payment3.printObject();

        System.out.println("\nInformationen zu den Transfers:");
        transfer1.printObject();
        System.out.println("-------------------------------");
        transfer2.printObject();
        System.out.println("-------------------------------");
        transfer3.printObject();

        // Ausgabe der Validierungsfehler
        System.out.println("\nValidierungsfehler bei Zahlungen:");
        invalidPayment.printObject();

        System.out.println("\nValidierungsfehler bei Transfers:");
        invalidTransfer.printObject();

    }
}