package task.userinterface.parser.types;

import task.constructs.commands.Command;
import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.ICommand;
import task.interfaces.IParser;
import task.validation.SyntaxValidator;

/**
 * Used for parsing a String to a command object.
 */
public class CommandParser implements IParser<ICommand> {
    @Override
    public ICommand parse(String[] inputArray) throws ParserException {
        try {
            SyntaxValidator.validateArray(inputArray)
                    .isNotEmpty()
                    .isOfLengthBetween(1, 2)
                    .throwIfInvalid("the passed command data array");

            String slug = inputArray[0];
            String[] arguments;

            if (inputArray.length > 1) {
                arguments = inputArray[1].split(";", -1);
            } else {
                arguments = null;
            }

            return new Command(slug, arguments);
        } catch (ValidationException validationException) {
            throw new ParserException("command", validationException.getMessage());
        }
    }
}