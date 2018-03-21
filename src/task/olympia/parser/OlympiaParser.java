package task.olympia.parser;

import task.exceptions.ParserException;
import task.olympia.parser.types.CommandParser;
import task.interfaces.ICommand;

/**
 * Holds instances of the parsers that belong to the olympia and passes the data to them.
 */
public class OlympiaParser {
    private CommandParser commandParser = new CommandParser();

    /**
     * Parses an input String to a new Command object.
     * @param input The String that shall be parsed to a command object.
     * @return Returns a new Command object if the parsing was successful.
     * @throws ParserException Thrown if parsing failed due to an invalid structure of the provided input data.
     */
    public ICommand parseCommand(String input) throws ParserException {
        return commandParser.parse(input);
    }

    /**
     * Parses an input String array to a new Command object.
     * @param inputArray The String array that shall be parsed, consisting of at least the command slug.
     * @return Returns a new Command object if the parsing was successful.
     * @throws ParserException Thrown if parsing failed due to an invalid structure of the provided input data.
     */
    public ICommand parseCommand(String[] inputArray) throws ParserException {
        return commandParser.parse(inputArray);
    }
}