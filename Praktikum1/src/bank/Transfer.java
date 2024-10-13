package bank;

// Die Transfer-Klasse erbt von der Payment-Klasse und repräsentiert Geldtransfers mit zusätzlichen Absender- und Empfängerinformationen.
public class Transfer{
    protected String date;  // Das Datum der Zahlung.
    protected double amount;  // Der Betrag der Zahlung.
    protected String description;  // Eine Beschreibung der Zahlung.
    private String sender = ""; // Der Absender des Geldtransfers.
    private String recipient = ""; // Der Empfänger des Geldtransfers.

    // Konstruktor für einen einfachen Geldtransfer ohne Absender- und Empfängerinformationen.
    public Transfer(String date, double amount, String description) {
        this.setDate(date);
        this.setAmount(amount);
        this.setDescription(description);
    }

    // Konstruktor für einen Geldtransfer mit Absender- und Empfängerinformationen.
    public Transfer(String date, double amount, String description, String sender, String recipient) {
        this(date, amount, description); // Ruft den einfachen Konstruktor auf.
        setSender(sender);
        setRecipient(recipient);
    }

    // Konstruktor zum Kopieren eines vorhandenen Geldtransfers.
    public Transfer(Transfer otherTransfer) {
        this.setDate(otherTransfer.getDate());
        this.setAmount(otherTransfer.getAmount());
        this.setDescription(otherTransfer.getDescription());
        this.setSender(otherTransfer.getSender());
        this.setRecipient(otherTransfer.getRecipient());
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setAmount(double amount) {
        if (amount >= 0) {
            this.amount = amount; // Ruft die Elternklasse-Methode auf.
        } else {
            System.out.println("Ungültige Eingabe für den Transferbetrag. Der Betrag muss positiv sein.");
        }
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }



    public void printObject() {
        System.out.println("Datum: " + date);
        System.out.println("Betrag: " + amount);
        System.out.println("Beschreibung: " + description);
        System.out.println("Absender: " + sender);
        System.out.println("Empfänger: " + recipient);
    }
}





