package bank;

import bank.exceptions.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.*;

public class PrivateBank implements Bank {

    private String name;
    private double incomingInterest;
    private double outgoingInterest;
    private Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();

    private String directoryName; // Default-Ordnername, kann in Konstruktor gesetzt werden


    // Constructor
    public PrivateBank(String name, double incomingInterest, double outgoingInterest, String directory) throws TransferPaymentAttributeException, IOException {
        this.name = name;
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
        this.directoryName = directory;
        readAccounts();
    }

    // Copy Constructor
    public PrivateBank(PrivateBank other) throws TransferPaymentAttributeException, IOException {
        this(other.getName(),other.getIncomingInterest(), other.getOutgoingInterest(), other.directoryName);
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
            } catch (AccountDoesNotExistException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void addTransaction(String account, Transaction transaction)
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IOException {
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
        writeAccount(account);
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
     * @param o Das Objekt, mit dem verglichen wird.
     * @return true, wenn die PrivateBank-Instanzen gleich sind, andernfalls false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivateBank that = (PrivateBank) o;
        return Double.compare(getIncomingInterest(), that.getIncomingInterest()) == 0 && Double.compare(getOutgoingInterest(), that.getOutgoingInterest()) == 0 && Objects.equals(getName(), that.getName()) && Objects.equals(accountsToTransactions, that.accountsToTransactions) && Objects.equals(directoryName, that.directoryName);
    }



    private void readAccounts() throws IOException {
        File directory = new File(directoryName);
        if (!directory.exists() || !directory.isDirectory()) {
            // Handle the case where the directory doesn't exist
            return;
        }

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                try (Reader reader = new FileReader(file)) {

                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Transaction.class, new TransactionDeserializer())
                            .create();

                    Type type = new TypeToken<List<Transaction>>() {}.getType();
                    List<Transaction> transactionsList = gson.fromJson(reader, type);

                    String accountName = file.getName().split(" ")[1].split("\\.")[0];
                    createAccount(accountName, transactionsList);
                    /*
                    accountsToTransactions.put(accountName, new ArrayList<>());

                    for (Transaction transaction : transactionsList){
                        accountsToTransactions.get(accountName).add(transaction);
                    }*/
                } catch (TransactionAlreadyExistException | AccountAlreadyExistsException |
                         TransactionAttributeException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // Method to write an account to the file system
    private void writeAccount(String accountName) {
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }

        List<Transaction> transactions = getTransactions(accountName);

        File file = new File(directory, "Konto " + accountName + ".json");
        try {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Transaction.class, new TransactionSerializer())
                    .setPrettyPrinting()
                    .create();

            JsonArray jsonArray = new JsonArray();

            for (Transaction e : transactions) {
                JsonObject transactionJson = new JsonObject();
                transactionJson.addProperty("CLASSNAME", e.getClass().getSimpleName());

                JsonObject instanceJson = (JsonObject) gson.toJsonTree(e);
                transactionJson.add("INSTANCE", instanceJson);

                jsonArray.add(transactionJson);
            }

            String json = gson.toJson(jsonArray);
            Files.write(file.toPath(), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
