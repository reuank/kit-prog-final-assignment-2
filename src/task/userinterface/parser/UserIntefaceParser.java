package task.userinterface.parser;

import task.exceptions.ParserException;
import task.interfaces.ICommand;
import task.userinterface.parser.types.CommandParser;
import task.userinterface.models.User;
import task.userinterface.models.UserGroup;
import task.userinterface.parser.types.UserParser;

public class UserIntefaceParser {
    private UserParser userParser = new UserParser();
    private CommandParser commandParser = new CommandParser();

    /**
     * Parses an input String to a new Command object.
     *
     * @param input The String that shall be parsed to a command object.
     * @return Returns a new Command object if the parsing was successful.
     * @throws ParserException Thrown if parsing failed due to an invalid structure of the provided input data.
     */
    public ICommand parseCommand(String input) throws ParserException {
        return commandParser.parse(input);
    }

    /**
     * Parses an input String array to a new Command object.
     *
     * @param inputArray The String array that shall be parsed, consisting of at least the command slug.
     * @return Returns a new Command object if the parsing was successful.
     * @throws ParserException Thrown if parsing failed due to an invalid structure of the provided input data.
     */
    public ICommand parseCommand(String[] inputArray) throws ParserException {
        return commandParser.parse(inputArray);
    }

    /**
     * Parses an input String array to a new user object.
     *
     * @param userGroup The user group the user shall be assigned to.
     * @param args The String array that shall be parsed, consisting of at least the command slug.
     * @return Returns a new user object if the parsing was successful.
     * @throws ParserException Thrown if parsing failed due to an invalid structure of the provided input data.
     */
    public User parseUser(UserGroup userGroup, String... args) throws ParserException {
        User user = parseUser(args);
        user.setUserGroup(userGroup);
        return user;
    }

    private User parseUser(String... args) throws ParserException {
        return userParser.parse(args);
    }
}