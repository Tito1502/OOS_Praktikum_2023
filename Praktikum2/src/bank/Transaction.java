package bank;

import java.util.Objects;

/**
 * Eine abstrakte Klasse, die eine Zahlungstransaktion repräsentiert.
 */
public abstract class Transaction implements CalculateBill {
    /**
     * Das Datum der Zahlung.
     */
    protected String date;

    /**
     * Der Betrag der Zahlung.
     */
    protected double amount;

    /**
     * Eine Beschreibung der Zahlung.
     */
    protected String description;

    /**
     * Konstruktor für die Transaction-Klasse.
     *
     * @param date        Das Datum der Zahlung.
     * @param amount      Der Betrag der Zahlung.
     * @param description Eine Beschreibung der Zahlung.
     */
    public Transaction(String date, double amount, String description) {
        setDate(date);
        setAmount(amount);
        setDescription(description);
    }

    /**
     * Setzt das Datum der Zahlung.
     *
     * @param date Das Datum der Zahlung.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Setzt den Betrag der Zahlung.
     *
     * @param amount Der Betrag der Zahlung.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Setzt die Beschreibung der Zahlung.
     *
     * @param description Eine Beschreibung der Zahlung.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gibt das Datum der Zahlung zurück.
     *
     * @return Das Datum der Zahlung.
     */
    public String getDate() {
        return date;
    }

    /**
     * Gibt den Betrag der Zahlung zurück.
     *
     * @return Der Betrag der Zahlung.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gibt die Beschreibung der Zahlung zurück.
     *
     * @return Eine Beschreibung der Zahlung.
     */
    public String getDescription() {
        return description;
    }


    /**
     * Gibt eine Zeichenfolge dar, die die Transaction-Instanz repräsentiert.
     *
     * @return Eine Zeichenfolge, die die Transaction-Instanz repräsentiert.
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "date='" + date + '\'' +
                ", amount=" + calculate() +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Überprüft, ob zwei Transaction-Instanzen gleich sind.
     *
     * @param o Das Objekt, mit dem verglichen wird.
     * @return true, wenn die Transaction-Instanzen gleich sind, andernfalls false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        return Double.compare(getAmount(), transaction.getAmount()) == 0 && getDate().equals(transaction.getDate()) && getDescription().equals(transaction.getDescription());
    }
}
