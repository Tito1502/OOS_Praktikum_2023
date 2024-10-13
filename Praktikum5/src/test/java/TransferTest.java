import bank.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import bank.Transfer;
public class TransferTest {
    private Transfer transfer;

    @BeforeEach
    void init() throws TransferPaymentAttributeException {
        transfer = new Transfer("01.01.2023", 100.0, "Test Transfer", "Sender", "Recipient");
    }

    @Test
    void testConstructor() {
        assertEquals("01.01.2023", transfer.getDate());
        assertEquals(100.0, transfer.getAmount());
        assertEquals("Test Transfer", transfer.getDescription());
        assertEquals("Sender", transfer.getSender());
        assertEquals("Recipient", transfer.getRecipient());
    }

    @Test
    void testCopyConstructor() throws TransferPaymentAttributeException {
        Transfer copiedTransfer = new Transfer(transfer);
        assertEquals(transfer, copiedTransfer);
        assertNotSame(transfer, copiedTransfer);
    }

    @Test
    void testCalculate() throws TransferPaymentAttributeException {
        assertEquals(100.0, transfer.calculate());
    }

    @Test
    void testSetAmount() throws TransferPaymentAttributeException {
        assertThrows(TransferPaymentAttributeException.class,() -> transfer.setAmount(-50.0));
        assertDoesNotThrow(() -> transfer.setAmount(50));
    }

    @Test
    void testEquals() throws TransferPaymentAttributeException {
        Transfer sameTransfer = new Transfer("01.01.2023", 100.0, "Test Transfer", "Sender", "Recipient");
        Transfer differentTransfer = new Transfer("02.02.2023", 50.0, "Different Transfer", "DifferentSender", "DifferentRecipient");

        assertEquals(transfer, sameTransfer);
        assertNotEquals(transfer, differentTransfer);
    }

    @Test
    void testToString() throws TransferPaymentAttributeException {
        String expectedToString = "Transaction{date='01.01.2023', amount=100.0, description='Test Transfer'} Transfer{sender='Sender', recipient='Recipient'} ";
        assertEquals(expectedToString, transfer.toString());
    }
}
