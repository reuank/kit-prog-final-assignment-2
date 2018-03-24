package task.exceptions;

import task.lang.Message;

/**
 * The exception thrown if a database problem occurred.
 */
public class DatabaseException extends IllegalCallOfCommandException {
    /**
     * Instantiates a new Database Exception.
     *
     * @param exception the exception message.
     */
    public DatabaseException(String exception) {
        super(exception);
    }

    /**
     * Instantiates a new Database Exception.
     *
     * @param exception the exception message.
     */
    public DatabaseException(Message exception) {
        super(exception);
    }
}
