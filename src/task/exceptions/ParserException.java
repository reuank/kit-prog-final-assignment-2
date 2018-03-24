package task.exceptions;

import task.lang.Message;

import static task.lang.Message.BECAUSE_$STRING$;
import static task.lang.Message.THE_$STRING$_COULD_NOT_BE_PARSED;

/**
 * The exception thrown if the passed input data could not be parsed to a database object.
 */
public class ParserException extends IllegalCallOfCommandException {
    /**
     * Instantiates an exception that shall be thrown if the passed input data could not be parsed to a database object.
     *
     * @param paramName The name of the parameter that was tried to be parsed.
     * @param exception The exception message.
     */
    public ParserException(String paramName, String exception) {
        super(Message.get(THE_$STRING$_COULD_NOT_BE_PARSED, paramName) + Message.get(BECAUSE_$STRING$, exception));
    }

    /**
     * Instantiates an exception that shall be thrown if the passed input data could not be parsed to a database object.
     *
     * @param exception The exception message.
     */
    public ParserException(String exception) {
        super(exception);
    }
}
