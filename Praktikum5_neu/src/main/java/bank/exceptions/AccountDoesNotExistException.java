package bank.exceptions;

/**
 * Exception thrown when attempting to perform an operation on an account that does not exist.
 */
public class AccountDoesNotExistException extends Exception {
    /**
     * Constructs an AccountDoesNotExistException with the specified detail message.
     *
     * @param message the detail message
     */
    public AccountDoesNotExistException(String message) {
        super(message);
    }
}
