package task.userinterface.parser.types;

import task.constructs.commands.Command;
import task.exceptions.ParserException;
import task.exceptions.ValidationException;
import task.interfaces.ICommand;
import task.interfaces.IParser;
import task.lang.Message;
import task.validation.SyntaxValidator;

import static task.lang.Message.COMMAND;
import static task.lang.Message.DATA;

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
                    .throwIfInvalid(Message.get(COMMAND) + " " + Message.get(DATA));

            String slug = inputArray[0];
            String[] arguments;

            if (inputArray.length > 1) {
                arguments = inputArray[1].split(";", -1);
            } else {
                arguments = null;
            }

            return new Command(slug, arguments);
        } catch (ValidationException validationException) {
            throw new ParserException(Message.get(COMMAND), validationException);
        }
    }
}