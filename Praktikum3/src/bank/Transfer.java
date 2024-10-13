package bank;

import bank.exceptions.*;

/**
 * Die Transfer-Klasse erbt von der Transaction-Klasse und repräsentiert Geldtransfers mit zusätzlichen Absender- und Empfängerinformationen.
 */
public class Transfer extends Transaction {
    /**
     * Der Absender des Geldtransfers.
     */
    private String sender = "";

    /**
     * Der Empfänger des Geldtransfers.
     */
    private String recipient = "";

    /**
     * Konstruktor für einen einfachen Geldtransfer ohne Absender- und Empfängerinformationen.
     *
     * @param date        Das Datum des Geldtransfers.
     * @param amount      Der Betrag des Geldtransfers.
     * @param description Eine Beschreibung des Geldtransfers.
     */
    public Transfer(String date, double amount, String description) throws TransferPaymentAttributeException {
        super(date, amount, description);
    }

    /**
     * Konstruktor für einen Geldtransfer mit Absender- und Empfängerinformationen.
     *
     * @param date        Das Datum des Geldtransfers.
     * @param amount      Der Betrag des Geldtransfers.
     * @param description Eine Beschreibung des Geldtransfers.
     * @param sender      Der Absender des Geldtransfers.
     * @param recipient   Der Empfänger des Geldtransfers.
     */
    public Transfer(String date, double amount, String description, String sender, String recipient) throws TransferPaymentAttributeException {
        this(date, amount, description); // Ruft den einfachen Konstruktor auf.
        setSender(sender);
        setRecipient(recipient);
    }

    /**
     * Konstruktor zum Kopieren eines vorhandenen Geldtransfers.
     *
     * @param otherTransfer Der zu kopierende Geldtransfer.
     */
    public Transfer(Transfer otherTransfer) throws TransferPaymentAttributeException {
        this(otherTransfer.date, otherTransfer.amount, otherTransfer.description, otherTransfer.sender, otherTransfer.recipient);
    }

    /**
     * Gibt den Absender des Geldtransfers zurück.
     *
     * @return Der Absender des Geldtransfers.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gibt den Empfänger des Geldtransfers zurück.
     *
     * @return Der Empfänger des Geldtransfers.
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Setzt den Betrag des Geldtransfers.
     *
     * @param amount Der Betrag des Geldtransfers.
     */
    @Override
    public void setAmount(double amount)  throws TransferPaymentAttributeException {
        if (amount >= 0) {
            this.amount = amount; // Ruft die Elternklasse-Methode auf.
        } else {
            throw new TransferPaymentAttributeException("Ungültige Eingabe für den Transferbetrag. Der Betrag muss positiv sein.");
        }
    }

    /**
     * Setzt den Absender des Geldtransfers.
     *
     * @param sender Der Absender des Geldtransfers.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Setzt den Empfänger des Geldtransfers.
     *
     * @param recipient Der Empfänger des Geldtransfers.
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Berechnet den Betrag des Geldtransfers.
     *
     * @return Der Betrag des Geldtransfers.
     */
    @Override
    public double calculate() {
        return amount;
    }

    /**
     * Gibt eine Zeichenfolge dar, die die Transfer-Instanz repräsentiert.
     *
     * @return Eine Zeichenfolge, die die Transfer-Instanz repräsentiert.
     */
    @Override
    public String toString() {
        return super.toString() +
                " Transfer{" +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                "} ";
    }

    /**
     * Überprüft, ob zwei Transfer-Instanzen gleich sind.
     *
     * @param o Das Objekt, mit dem verglichen wird.
     * @return true, wenn die Transfer-Instanzen gleich sind, andernfalls false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Transfer transfer = (Transfer) o;
        return getSender().equals(transfer.getSender()) && getRecipient().equals(transfer.getRecipient()) && super.equals(transfer);
    }
}
