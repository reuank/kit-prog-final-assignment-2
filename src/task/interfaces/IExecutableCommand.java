package task.interfaces;

import task.constructs.commands.CommandSignature;
import task.exceptions.IllegalCallOfCommandException;
import task.exceptions.ValidationException;
import task.lang.Message;

import java.util.List;

import static task.lang.Message.BUT;
import static task.lang.Message.COMMAND_$STRING$_COULD_NOT_BE_EXECUTED;
import static task.lang.Message.REQUIRED_COMMAND_SIGNATURE_IS_$STRING$;

/**
 * An interface for executable commands.
 */
public interface IExecutableCommand extends ICommand {
    /**
     * Tries to execute the given command using the processes defined in the database command class.
     * @param command The command that shall be executed.
     * @param outputStream The place where all the output of the command - except errors - goes.
     * @throws IllegalCallOfCommandException Thrown if any errors occur during the execution of the command.
     */
    default void tryToExecute(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        try {
            this.validateCommand(command);
            executeCommand(command, outputStream);
        } catch (ValidationException validationException) {
            StringBuilder message = new StringBuilder();

            message.append(Message.get(COMMAND_$STRING$_COULD_NOT_BE_EXECUTED, this.getSlug())).append(" ");
            message.append(Message.get(REQUIRED_COMMAND_SIGNATURE_IS_$STRING$,
                    this.getCommandSignature().getCommandSignature())).append(", ");
            message.append(BUT.get()).append(" ").append(validationException.getMessage());

            throw new IllegalCallOfCommandException(message.toString());
        }
    }

    /**
     * The actual execution steps of a command.
     * @param command the command
     * @param outputStream the output strean a command can write on
     * @throws IllegalCallOfCommandException thrown if something went wrong during the execution of the command.
     */
    void executeCommand(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException;

    /**
     * @return - returns the command signature.
     */
    CommandSignature getCommandSignature();

    /**
     * Checks whether a passed command can be executed by this command.
     *
     * @param command the command that shall be checked.
     * @throws ValidationException thrown if the command data is invalid.
     */
    void validateCommand(ICommand command) throws ValidationException;

    @Override
    default String getSlug() {
        return getCommandSignature().getSlug();
    }

    @Override
    default String[] getArgs() {
        return getCommandSignature().getArgNames();
    }

    @Override
    default String getArg(int index) {
        return getCommandSignature().getArgName(index);
    }
}