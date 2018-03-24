package task.exceptions;

/**
 * The exception thrown if an authentication problem occurred.
 */
public class AuthException extends IllegalCallOfCommandException {
    /**
     * Instantiates a new Auth exception.
     *
     * @param exception The exception message.
     */
    public AuthException(String exception) {
        super(exception);
    }
}
