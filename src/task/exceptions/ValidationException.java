package task.exceptions;

/**
 * The exception thrown if the validation process failed.
 */
public class ValidationException extends Exception {
    /**
     * Instantiates an exception that shall be thrown if the validation process failed.
     * @param exception The validation exception message that has been build during the validation process.
     */
    public ValidationException(String exception) {
        super(exception);
    }

    /**
     * Instantiates an exception that shall be thrown if the validation process failed.
     * @param paramName The param whose validation has failed.
     * @param exception The validation exception message that has been build during the validation process.
     */
    public ValidationException(String paramName, String exception) {
        super(String.format("%s %s.", paramName, exception));
    }
}