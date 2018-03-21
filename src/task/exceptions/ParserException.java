package task.exceptions;

/**
 * The exception thrown if the passed input data could not be parsed to a database object.
 */
public class ParserException extends Exception {
    /**
     * Instantiates an exception that shall be thrown if the passed input data could not be parsed to a database object.
     * @param paramName The name of the parameter that was tried to be parsed.
     * @param exception The exception message.
     */
    public ParserException(String paramName, String exception) {
        super(String.format("the %s could not be parsed because %s", paramName, exception));
    }

    public ParserException(String exception) {
        super(exception);
    }
}
