package bank.exceptions;

/**
 * Exception thrown when attempting to create an account that already exists.
 */
public class AccountAlreadyExistsException extends Exception {
    /**
     * Constructs an AccountAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}