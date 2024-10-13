package bank;

import bank.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrivateBank implements Bank {

    private String name;
    private double incomingInterest;
    private double outgoingInterest;
    private Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();

    // Constructor
    public PrivateBank(String name, double incomingInterest, double outgoingInterest) throws TransferPaymentAttributeException {
        this.name = name;
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    // Copy Constructor
    public PrivateBank(PrivateBank other) throws TransferPaymentAttributeException {
        this(other.getName(),other.getIncomingInterest(), other.getOutgoingInterest());
        this.accountsToTransactions = new HashMap<>(other.accountsToTransactions);
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getIncomingInterest(){
        return incomingInterest;
    }

    public void setIncomingInterest(double incomingInterest)  throws TransferPaymentAttributeException{
        if (incomingInterest >= 0 && incomingInterest <= 1) {
            this.incomingInterest = incomingInterest;
        } else {
            throw new TransferPaymentAttributeException("Ungültige Eingabe für den eingehenden Zinssatz. Er muss zwischen 0 und 1 liegen.");
        }
    }

    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    public void setOutgoingInterest(double outgoingInterest) throws TransferPaymentAttributeException{
        if (outgoingInterest >= 0 && outgoingInterest <= 1) {
            this.outgoingInterest = outgoingInterest;
        } else {
            throw new TransferPaymentAttributeException("Ungültige Eingabe für den ausgehenden Zinssatz. Er muss zwischen 0 und 1 liegen.");
        }
    }

    // Implementing methods from the Bank interface

    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account already exists: " + account);
        }
        accountsToTransactions.put(account, new ArrayList<>());
    }

    @Override
    public void createAccount(String account, List<Transaction> transactions)
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account already exists: " + account);
        }

        accountsToTransactions.put(account, new ArrayList<>());
        for (Transaction transaction : transactions) {
            try {
                addTransaction(account, transaction);
            } catch (AccountDoesNotExistException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void addTransaction(String account, Transaction transaction)
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account does not exist: " + account);
        }

        if (containsTransaction(account, transaction)) {
            throw new TransactionAlreadyExistException("Transaction already exists: " + transaction);
        }

        // Überprüfen, ob es sich um eine Payment-Transaktion handelt
        if (transaction instanceof Payment payment) {

            // Setzen der incomingInterest und outgoingInterest Werte aus der PrivateBank
            try {
                payment.setIncomingInterest(incomingInterest);
                payment.setOutgoingInterest(outgoingInterest);
            } catch (TransferPaymentAttributeException e) {
                throw new RuntimeException(e);
            }
        }

        accountsToTransactions.get(account).add(transaction);
    }

    @Override
    public void removeTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException, TransactionDoesNotExistException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account does not exist: " + account);
        }

        if (!containsTransaction(account, transaction)) {
            throw new TransactionDoesNotExistException("Transaction does not exist: " + transaction);
        }

        accountsToTransactions.get(account).remove(transaction);
    }

    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        return accountsToTransactions.getOrDefault(account, new ArrayList<>()).contains(transaction);
    }

    @Override
    public double getAccountBalance(String account) {
        double balance = 0;
        for (Transaction transaction : accountsToTransactions.getOrDefault(account, new ArrayList<>())) {
            balance += transaction.calculate();
        }

        return balance;
    }

    @Override
    public List<Transaction> getTransactions(String account) {
        return new ArrayList<>(accountsToTransactions.getOrDefault(account, new ArrayList<>()));
    }

    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        List<Transaction> transactions = getTransactions(account);
        transactions.sort((t1, t2) -> asc ? Double.compare(t1.calculate(), t2.calculate()) : Double.compare(t2.calculate(), t1.calculate()));
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : getTransactions(account)) {
            if (positive && transaction.calculate() > 0) {

                transactions.add(transaction);
            } else if (!positive && transaction.calculate() < 0) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }


    /**
     * Gibt eine Zeichenfolge dar, die die PrivateBank-Instanz repräsentiert.
     *
     * @return Eine Zeichenfolge, die die PrivateBank-Instanz repräsentiert.
     */
    @Override
    public String toString() {
        return "PrivateBank{" +
                "name='" + name + '\'' +
                ", incomingInterest=" + incomingInterest +
                ", outgoingInterest=" + outgoingInterest +
                ", accountsToTransactions=" + accountsToTransactions +
                '}';
    }

    /**
     * Überprüft, ob zwei PrivateBank-Instanzen gleich sind.
     *
     * @param obj Das Objekt, mit dem verglichen wird.
     * @return true, wenn die PrivateBank-Instanzen gleich sind, andernfalls false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        PrivateBank that = (PrivateBank) obj;

        return (Double.compare(that.incomingInterest, incomingInterest) != 0) && (Double.compare(that.outgoingInterest, outgoingInterest) != 0) && (!name.equals(that.name)) && accountsToTransactions.equals(that.accountsToTransactions);
    }
}
