import bank.exceptions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import bank.*;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PrivateBankTest {
    private PrivateBank bank;

    @TempDir
    static Path sharedTempDir;

    @BeforeEach
    void init() throws IOException, TransferPaymentAttributeException {
        // Initialize your Bank implementation before each test
        System.out.println(sharedTempDir.toAbsolutePath().toString());
        bank = new PrivateBank("TestBank", 0.1, 0.2, sharedTempDir.toAbsolutePath().toString());
    }

    @Test
    void testConstructor() {
        // Hier können Sie Tests für den Konstruktor durchführen
        assertEquals("TestBank", bank.getName());
        assertEquals(0.1, bank.getIncomingInterest());
        assertEquals(0.2, bank.getOutgoingInterest());
        // Weitere Tests je nach Bedarf
    }

    @Test
    public void testCopyConstructor() throws TransferPaymentAttributeException, IOException {
        PrivateBank copyBank = new PrivateBank(bank);
        assertEquals(bank, copyBank);
        assertNotSame(bank, copyBank);
        assertDoesNotThrow(() ->copyBank.setName("test"));
        assertNotEquals(bank, copyBank);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Account10", "Account20", "Account30"})
    void testCreateAccount(String accountName) {
        assertDoesNotThrow(() -> bank.createAccount(accountName));
        assertThrows(AccountAlreadyExistsException.class, () -> bank.createAccount(accountName));
    }

    @Test
    void testCreateAccountWithTransactions() throws TransferPaymentAttributeException {
        // Test createAccount method with transactions
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Payment("01.01.2023", 100.0, "Test Payment"));
        transactions.add(new OutgoingTransfer("26.10.2023", 2500, "Test Transfer 1", "Account2", "Account10"));
        transactions.add(new IncomingTransfer("26.10.2023", 450.0, "Test Transfer 2", "Account10", "Account2"));
        assertDoesNotThrow(() -> bank.createAccount("Account2", transactions));
        assertEquals(3, bank.getTransactions("Account2").size());
    }

    @Test
    void testAddTransaction() throws AccountAlreadyExistsException, TransferPaymentAttributeException {
        // Test addTransaction method

        bank.createAccount("Account3");
        Payment payment = new Payment("01.01.2023", 100.0, "Test Payment");
        assertDoesNotThrow(() -> bank.addTransaction("Account3", payment));
        assertTrue(bank.containsTransaction("Account3", payment));
        assertThrows(AccountDoesNotExistException.class,() -> bank.addTransaction("Account30", payment));
        assertThrows(TransactionAlreadyExistException.class,() -> bank.addTransaction("Account3", payment));
    }

    @Test
    void testRemoveTransaction() throws AccountAlreadyExistsException, TransferPaymentAttributeException {
        // Test removeTransaction method
        bank.createAccount("Account4");
        Payment payment = new Payment("01.01.2023", 100.0, "Test Payment");
        assertDoesNotThrow(() -> bank.addTransaction("Account4", payment));
        assertTrue(bank.containsTransaction("Account4", payment));
        assertDoesNotThrow(() -> bank.removeTransaction("Account4", payment));
        assertFalse(bank.containsTransaction("Account4", payment));

        assertThrows(AccountDoesNotExistException.class,() -> bank.removeTransaction("Account40", payment));

        Payment payment2 = new Payment("02.01.2023", 150.0, "Test Payment");
        assertThrows(TransactionDoesNotExistException.class,() -> bank.removeTransaction("Account4", payment2));
    }

    @Test
    void testGetAccountBalance() throws TransferPaymentAttributeException {
        // Test getAccountBalance method
        assertDoesNotThrow(() -> bank.createAccount("Account5"));
        assertEquals(0.0, bank.getAccountBalance("Account5"));
        Payment payment = new Payment("01.01.2023", 100.0, "Test Payment");
        assertDoesNotThrow(() -> bank.addTransaction("Account5",payment));
        assertEquals(90.0, bank.getAccountBalance("Account5"));
    }

    @Test
    void testGetTransactions() {
        // Test getTransactions method
        try {
            bank.createAccount("Account6");
            Payment payment = new Payment("01.01.2023", 100.0, "Test Payment");
            assertDoesNotThrow(() -> bank.addTransaction("Account6", payment));
            assertEquals(1, bank.getTransactions("Account6").size());

            OutgoingTransfer outgoingtransfer = new OutgoingTransfer("26.10.2023", 2500, "Test Transfer 1", "Account2", "Account10");
            assertDoesNotThrow(() -> bank.addTransaction("Account6", outgoingtransfer));
            assertEquals(2, bank.getTransactions("Account6").size());
        } catch (Exception e) {
            fail("Exception during test: " + e.getMessage());
        }
    }

    @Test
    void testGetTransactionsSorted() {
        // Test getTransactionsSorted method
        try {
            bank.createAccount("Account7");
            Payment payment1 = new Payment("01.01.2023", 100.0, "Test Payment 1");
            Payment payment2 = new Payment("01.01.2023", 200.0, "Test Payment 2");
            OutgoingTransfer transfer1 = new OutgoingTransfer("01.01.2023",100.0,"Test Tranfer 1", "Account7","Account1");
            assertDoesNotThrow(() -> bank.addTransaction("Account7", payment1));
            assertDoesNotThrow(() -> bank.addTransaction("Account7", payment2));
            assertDoesNotThrow(() -> bank.addTransaction("Account7", transfer1));
            List<Transaction> sortedTransactionsAsc = bank.getTransactionsSorted("Account7", true);
            List<Transaction> sortedTransactionsDesc = bank.getTransactionsSorted("Account7", false);
            assertEquals(-100.0, sortedTransactionsAsc.get(0).calculate());
            assertEquals(180.0, sortedTransactionsDesc.get(0).calculate());
        } catch (Exception e) {
            fail("Exception during test: " + e.getMessage());
        }
    }

    @Test
    void testGetTransactionsByType() {
        // Test getTransactionsByType method
        try {
            bank.createAccount("Account8");
            Payment payment1 = new Payment("01.01.2023", 100.0, "Test Payment 1");
            Payment payment2 = new Payment("01.01.2023", -50.0, "Test Payment 2");
            OutgoingTransfer transfer1 = new OutgoingTransfer("01.01.2023",100.0,"Test Tranfer 1", "Account8","Account1");
            assertDoesNotThrow(() -> bank.addTransaction("Account8", payment1));
            assertDoesNotThrow(() -> bank.addTransaction("Account8", payment2));
            assertDoesNotThrow(() -> bank.addTransaction("Account8", transfer1));
            List<Transaction> positiveTransactions = bank.getTransactionsByType("Account8", true);
            List<Transaction> negativeTransactions = bank.getTransactionsByType("Account8", false);
            assertEquals(1, positiveTransactions.size());
            assertEquals(2, negativeTransactions.size());
        } catch (Exception e) {
            fail("Exception during test: " + e.getMessage());
        }
    }

    @Test
    public void testEquals() throws TransferPaymentAttributeException, IOException {
        PrivateBank equalBank = new PrivateBank("TestBank", 0.1, 0.2, sharedTempDir.toAbsolutePath().toString());
        PrivateBank differentBank = new PrivateBank("TestBank2", 0.2, 0.2, sharedTempDir.toAbsolutePath().toString());

        assertEquals(bank, equalBank);
        assertNotEquals(bank, differentBank);
    }

    @Test
    public void testToString() throws IOException, TransferPaymentAttributeException {
        PrivateBank toStringBank = new PrivateBank("TestBank", 0.1, 0.2, "test");
        String expectedToString = "PrivateBank{name='TestBank', incomingInterest=0.1, outgoingInterest=0.2, accountsToTransactions={}}";
        assertEquals(expectedToString, toStringBank.toString());
    }

    @Test
    public void setInterest() throws TransferPaymentAttributeException {
        assertDoesNotThrow(() -> bank.setIncomingInterest(0.5));
        assertDoesNotThrow(() -> bank.setOutgoingInterest(0.7));
        assertThrows(TransferPaymentAttributeException.class,() -> bank.setIncomingInterest(-1));
        assertThrows(TransferPaymentAttributeException.class,() -> bank.setIncomingInterest(2));
        assertThrows(TransferPaymentAttributeException.class,() -> bank.setOutgoingInterest(-1.7));
        assertThrows(TransferPaymentAttributeException.class,() -> bank.setOutgoingInterest(1.7));
    }
}
