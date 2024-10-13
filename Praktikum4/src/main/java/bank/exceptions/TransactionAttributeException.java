package bank.exceptions;

/**
 * Exception thrown when there is an issue with the attributes of a transaction.
 * This can include validation failures for certain attributes.
 */
public class TransactionAttributeException extends Exception {
    /**
     * Constructs a TransactionAttributeException with the specified detail message.
     *
     * @param message the detail message
     */
    public TransactionAttributeException(String message) {
        super(message);
    }
}
