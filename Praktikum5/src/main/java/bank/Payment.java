package bank;

import bank.exceptions.TransferPaymentAttributeException;


/**
 * Die Payment-Klasse erbt von der Transaction-Klasse und repräsentiert Geldtransfers mit Zinssatz.
 */
public class Payment extends Transaction {
    /**
     * Der Zinssatz für eingehende Zahlungen (Standardwert 0).
     */
    private double incomingInterest = 0.0;

    /**
     * Der Zinssatz für ausgehende Zahlungen (Standardwert 0).
     */
    private double outgoingInterest = 0.0;

    /**
     * Konstruktor für eine einfache Zahlung ohne Zinsen.
     *
     * @param date        Das Datum der Zahlung.
     * @param amount      Der Betrag der Zahlung.
     * @param description Eine Beschreibung der Zahlung.
     */
    public Payment(String date, double amount, String description) throws TransferPaymentAttributeException {
        super(date, amount, description);
    }

    /**
     * Konstruktor für eine Zahlung mit Zinsen.
     *
     * @param date             Das Datum der Zahlung.
     * @param amount           Der Betrag der Zahlung.
     * @param description      Eine Beschreibung der Zahlung.
     * @param incomingInterest Der Zinssatz für eingehende Zahlungen.
     * @param outgoingInterest Der Zinssatz für ausgehende Zahlungen.
     */
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest) throws TransferPaymentAttributeException {
        this(date, amount, description);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    /**
     * Konstruktor zum Kopieren einer vorhandenen Zahlung.
     *
     * @param otherPayment Die zu kopierende Zahlung.
     */
    public Payment(Payment otherPayment) throws TransferPaymentAttributeException {
        this(otherPayment.date, otherPayment.amount, otherPayment.description, otherPayment.incomingInterest, otherPayment.outgoingInterest);
    }

    /**
     * Gibt den Zinssatz für eingehende Zahlungen zurück.
     *
     * @return Der Zinssatz für eingehende Zahlungen.
     */
    public double getIncomingInterest() {
        return incomingInterest;
    }

    /**
     * Gibt den Zinssatz für ausgehende Zahlungen zurück.
     *
     * @return Der Zinssatz für ausgehende Zahlungen.
     */
    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    /**
     * Setter-Methode, um den Zinssatz für eingehende Zahlungen zu setzen.
     *
     * @param incomingInterest Der Zinssatz für eingehende Zahlungen.
     */
    public void setIncomingInterest(double incomingInterest) throws TransferPaymentAttributeException {
        if (incomingInterest >= 0 && incomingInterest <= 1) {
            this.incomingInterest = incomingInterest;
        } else {
            throw new TransferPaymentAttributeException("Ungültige Eingabe für den eingehenden Zinssatz. Er muss zwischen 0 und 1 liegen.");
        }
    }

    /**
     * Setter-Methode, um den Zinssatz für ausgehende Zahlungen zu setzen.
     *
     * @param outgoingInterest Der Zinssatz für ausgehende Zahlungen.
     */
    public void setOutgoingInterest(double outgoingInterest) throws TransferPaymentAttributeException {
        if (outgoingInterest >= 0 && outgoingInterest <= 1) {
            this.outgoingInterest = outgoingInterest;
        } else {
            throw new TransferPaymentAttributeException("Ungültige Eingabe für den ausgehenden Zinssatz. Er muss zwischen 0 und 1 liegen.");
        }
    }


    /**
     * Berechnet den Betrag der Zahlung unter Berücksichtigung der Zinsen.
     *
     * @return Der berechnete Betrag der Zahlung.
     */
    @Override
    public double calculate() {
        if (amount > 0) {
            return amount * (1.0 - this.incomingInterest);
        } else if (amount < 0) {
            return amount * (1.0 + this.outgoingInterest);
        }

        return 0;
    }

    /**
     * Gibt eine Zeichenfolge dar, die die Payment-Instanz repräsentiert.
     *
     * @return Eine Zeichenfolge, die die Payment-Instanz repräsentiert.
     */
    @Override
    public String toString() {
        return super.toString() +
                " Payment{" +
                "incomingInterest=" + incomingInterest +
                ", outgoingInterest=" + outgoingInterest +
                "} ";
    }

    /**
     * Überprüft, ob zwei Payment-Instanzen gleich sind.
     *
     * @param o Das Objekt, mit dem verglichen wird.
     * @return true, wenn die Payment-Instanzen gleich sind, andernfalls false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Payment payment = (Payment) o;
        return Double.compare(getIncomingInterest(), payment.getIncomingInterest()) == 0 && Double.compare(getOutgoingInterest(), payment.getOutgoingInterest()) == 0 && super.equals(payment);
    }
}
