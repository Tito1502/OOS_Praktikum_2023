import bank.Payment;
import bank.exceptions.TransferPaymentAttributeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PaymentTest {
    private Payment payment;

    @BeforeEach
    public void init() throws TransferPaymentAttributeException {
        payment = new Payment("01.01.2023", 100.0, "Test Payment", 0.1, 0.2);
    }

    @Test
    public void testConstructor() {
        assertEquals("01.01.2023", payment.getDate());
        assertEquals(100.0, payment.getAmount(), 0.001);
        assertEquals("Test Payment", payment.getDescription());
        assertEquals(0.1, payment.getIncomingInterest(), 0.001);
        assertEquals(0.2, payment.getOutgoingInterest(), 0.001);
    }

    @Test
    public void testCopyConstructor() throws TransferPaymentAttributeException {
        Payment copyPayment = new Payment(payment);
        assertEquals(payment, copyPayment);
        assertNotSame(payment, copyPayment);
    }


    @Test
    public void testCalculate() {
        assertEquals(90.0, payment.calculate());
        assertDoesNotThrow(() -> payment.setAmount(-50.0));
        assertEquals(-60.0, payment.calculate());
        assertDoesNotThrow(() -> payment.setAmount(0));
        assertEquals(0, payment.calculate());
    }

    @Test
    public void testEquals() throws TransferPaymentAttributeException {
            Payment equalPayment = new Payment("01.01.2023", 100.0, "Test Payment", 0.1, 0.2);
            Payment differentPayment = new Payment("02.01.2023", 200.0, "Different Payment", 0.2, 0.3);

            assertEquals(payment, equalPayment);
            assertNotEquals(payment, differentPayment);
    }

    /*
        try {
        } catch (TransferPaymentAttributeException e) {
            // Handle die Ausnahme hier, wenn sie beim Kopieren auftritt
            fail("Exception should not be thrown in this case: " + e.getMessage());
        }
     */

    @Test
    public void testToString() {
        String expectedToString = "Transaction{date='01.01.2023', amount=90.0, description='Test Payment'} Payment{incomingInterest=0.1, outgoingInterest=0.2} ";
        assertEquals(expectedToString, payment.toString());
    }

    @Test
    public void testSetIncomingInterest() {
        assertDoesNotThrow(() -> payment.setIncomingInterest(0.3));
        assertEquals(0.3, payment.getIncomingInterest(), 0.001);

        assertThrows(TransferPaymentAttributeException.class, () -> payment.setIncomingInterest(-0.1));
        assertThrows(TransferPaymentAttributeException.class, () -> payment.setIncomingInterest(1.1));
    }

    @Test
    public void testSetOutgoingInterest() {
        assertDoesNotThrow(() -> payment.setOutgoingInterest(0.4));
        assertEquals(0.4, payment.getOutgoingInterest(), 0.001);

        assertThrows(TransferPaymentAttributeException.class, () -> payment.setOutgoingInterest(-0.1));
        assertThrows(TransferPaymentAttributeException.class, () -> payment.setOutgoingInterest(1.1));
    }
}
