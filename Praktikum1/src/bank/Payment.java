package bank;

// Die Payment-Klasse repräsentiert Zahlungen mit verschiedenen Attributen.
public class Payment {
    protected String date;  // Das Datum der Zahlung.
    protected double amount;  // Der Betrag der Zahlung.
    protected String description;  // Eine Beschreibung der Zahlung.
    private double incomingInterest = 0.0;  // Der Zinssatz für eingehende Zahlungen (Standardwert 0).
    private double outgoingInterest = 0.0;  // Der Zinssatz für ausgehende Zahlungen (Standardwert 0).

    // Konstruktor für eine einfache Zahlung ohne Zinsen.
    public Payment(String date, double amount, String description) {
        setDate(date);
        setAmount(amount);
        setDescription(description);
    }

    // Konstruktor für eine Zahlung mit Zinsen.
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest) {
        this(date, amount, description);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    // Konstruktor zum Kopieren einer vorhandenen Zahlung.
    public Payment(Payment otherPayment) {
        this.setDate(otherPayment.getDate());
        this.setAmount(otherPayment.getAmount());
        this.setDescription(otherPayment.getDescription());
        this.setIncomingInterest(otherPayment.getIncomingInterest());
        this.setOutgoingInterest(otherPayment.getOutgoingInterest());
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

    public double getIncomingInterest() {
        return incomingInterest;
    }

    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Setter-Methode, um den Betrag der Zahlung zu setzen.
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Setter-Methode, um den Zinssatz für eingehende Zahlungen zu setzen.
    private void setIncomingInterest(double incomingInterest) {
        if (incomingInterest >= 0 && incomingInterest <= 1) {
            this.incomingInterest = incomingInterest;
        } else {
            System.out.println("Ungültige Eingabe für den eingehenden Zinssatz. Er muss zwischen 0 und 1 liegen.");
        }
    }

    // Setter-Methode, um den Zinssatz für ausgehende Zahlungen zu setzen.
    private void setOutgoingInterest(double outgoingInterest) {
        if (outgoingInterest >= 0 && outgoingInterest <= 1) {
            this.outgoingInterest = outgoingInterest;
        } else {
            System.out.println("Ungültige Eingabe für den ausgehenden Zinssatz. Er muss zwischen 0 und 1 liegen.");
        }
    }

    // Methode zur Ausgabe aller Informationen über die Zahlung.
    public void printObject() {
        System.out.println("Datum: " + date);
        System.out.println("Betrag: " + amount);
        System.out.println("Beschreibung: " + description);
        System.out.println("Zinssatz für eingehende Zahlungen: " + incomingInterest);
        System.out.println("Zinssatz für ausgehende Zahlungen: " + outgoingInterest);
    }
}


/*
    */