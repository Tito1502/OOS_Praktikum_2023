package bank.exceptions;

/**
 * Exception thrown when there is an issue with the attributes of a Transfer or Payment transaction.
 * This can include validation failures for certain attributes specific to Transfer and Payment transactions.
 */
public class TransferPaymentAttributeException extends Exception {
    /**
     * Constructs a TransferPaymentAttributeException with the specified detail message.
     *
     * @param message the detail message
     */
    public TransferPaymentAttributeException(String message) {
        super(message);
    }
}