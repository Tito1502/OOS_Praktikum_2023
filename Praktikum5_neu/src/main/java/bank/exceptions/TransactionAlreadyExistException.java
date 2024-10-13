package bank.exceptions;

/**
 * Exception thrown when attempting to add a transaction that already exists.
 */
public class TransactionAlreadyExistException extends Exception {
    /**
     * Constructs a TransactionAlreadyExistException with the specified detail message.
     *
     * @param message the detail message
     */
    public TransactionAlreadyExistException(String message) {
        super(message);
    }
}
