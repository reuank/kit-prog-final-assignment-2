package task.interfaces;

import task.exceptions.ParserException;

/**
 * An interface for parsers.
 */
public interface IParser<T> {
    /**
     * Parses an input String to a database Object.
     * @param args The String that shall be parsed.
     * @return Returns a new  object instance of the class.
     * @throws ParserException Thrown if parsing is impossible.
     */
    default T parse(String args) throws ParserException {
        String[] inputArray = args.split("\\s", 2);
        return parse(inputArray);
    }

    /**
     * Parses an input String array to a database Object.
     * @param args The String array that shall be parsed.
     * @return Returns a new  object instance of the class.
     * @throws ParserException Thrown if parsing is impossible.
     */
    T parse(String[] args) throws ParserException;
}