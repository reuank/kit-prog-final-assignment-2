package task.exceptions;

import task.lang.Message;

import static task.lang.Message.BUT;
import static task.lang.Message.COMMAND_$STRING$_COULD_NOT_BE_EXECUTED;
import static task.lang.Message.REQUIRED_COMMAND_SIGNATURE_IS_$STRING$;

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