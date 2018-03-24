package task.userinterface.validation.types;

import task.constructs.commands.CommandSignature;
import task.exceptions.ValidationException;
import task.interfaces.ICommand;
import task.validation.SyntaxValidator;

/**
 * Used to execute the validation of a command. Performs the necessary semantic checks on the command data.
 */
public class CommandValidation {
    private ICommand validateMe;

    /**
     * Instanciates a new command validation object, in which error messages can be chained together.
     * @param command The already parsed and syntactically correct command data, that shall now be validated for
     *                semantic correctness.
     * @param commandSignature The command signature the parsed command shall be checked against.
     * @throws ValidationException Thrown, if the passed command is semantically incorrect.
     */
    public CommandValidation(ICommand command, CommandSignature commandSignature) throws ValidationException {
        this.validateMe = command;
        validate(validateMe, commandSignature);
    }

    /**
     * Executes the command validation chain that consists of multiple semantic checks.
     * @param command The passed command data that shall be validated.
     * @param commandSignature The command signature the parsed command shall be checked against.
     * @throws ValidationException Thrown, if the passed command is semantically incorrect.
     */
    private void validate(ICommand command, CommandSignature commandSignature) throws ValidationException {
        SyntaxValidator.validateString(command.getSlug())
                .isNotNull()
                .isExactly(commandSignature.getSlug())
                .throwIfInvalid("command slug");

        SyntaxValidator.validateArray(command.getArgs())
                .isOfLength(commandSignature.getArgCount())
                .throwIfInvalid("command arguments list") // already throw an error here (list has wrong length)
                .checkTypes(commandSignature.getArgTypes())
                .throwIfInvalid("command parameter");
    }

    /**
     * Returns the validated command.
     * @return The validated command.
     */
    public ICommand getResult() {
        return this.validateMe;
    }
}