package bank.exceptions;

/**
 * Exception thrown when attempting to perform an operation on a transaction that does not exist.
 */
public class TransactionDoesNotExistException extends Exception {
    /**
     * Constructs a TransactionDoesNotExistException with the specified detail message.
     *
     * @param message the detail message
     */
    public TransactionDoesNotExistException(String message) {
        super(message);
    }
}
