package task.userinterface.validation;

import task.constructs.commands.CommandSignature;
import task.exceptions.ValidationException;
import task.userinterface.validation.types.CommandValidation;
import task.interfaces.ICommand;

/**
 * The class that manages all the available type validations.
 * This way all the available validations can be accessed via Intellisense by typing "InputValidator."
 */
public class InputValidator {
    /**
     * Validates a parsed Command object for semantic correctness.
     * @param cmd The command that shall be validated.
     * @param sign The signature that the command shall be checked against.
     * @return Returns a new validation-object, which could be used for chaining further validations.
     * @throws ValidationException Thrown if the passed command data is incorrect.
     */
    public CommandValidation validateCommand(ICommand cmd, CommandSignature sign) throws ValidationException {
        return new CommandValidation(cmd, sign);
    }


}