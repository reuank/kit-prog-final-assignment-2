package task.exceptions;

/**
 * The exception thrown if a command was called without correct and executable parameters.
 */
public class InvalidCallOfCommandException extends Exception {
    /**
     * Instantiates an exception that shall be thrown if a command was called without correct and executable parameters.
     * @param exception The exception message.
     */
    public InvalidCallOfCommandException(String exception) {
        super(exception);
    }

    /**
     * Instantiates a Exceptions that shall be thrown if a command was called without correct and executable parameters.
     * @param slug The command slug of the command that was tried to be executed.
     * @param signature The signature of the command that was tried to be executed.
     * @param validationMessage The validation message that was generated during the semantic validation.
     */
    public InvalidCallOfCommandException(String slug, String signature, String validationMessage) {
        super(String.format("command \"%s\" could not be executed. The required structure is \"%s\", but the %s",
                slug,
                signature,
                validationMessage));
    }
}