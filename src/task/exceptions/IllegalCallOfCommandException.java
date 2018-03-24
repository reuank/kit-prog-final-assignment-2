package task.exceptions;

import task.lang.Message;

/**
 * The exception thrown if a command was called without correct and executable parameters.
 */
public class IllegalCallOfCommandException extends Exception {
    /**
     * Instantiates an exception that shall be thrown if a command was called without correct and executable parameters.
     *
     * @param exception The exception message.
     */
    public IllegalCallOfCommandException(String exception) {
        super(exception);
    }

    /**
     * Instantiates an exception that shall be thrown if a command was called without correct and executable parameters.
     *
     * @param exception The exception message.
     */
    public IllegalCallOfCommandException(Message exception) {
        super(exception.get());
    }
}