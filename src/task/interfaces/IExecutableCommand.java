package task.interfaces;

import task.exceptions.InvalidCallOfCommandException;

import java.util.List;

/**
 * An interface for executable commands.
 */
public interface IExecutableCommand extends ICommand {
    /**
     * Tries to execute the given command using the processes defined in the database command class.
     * @param command The command that shall be executed.
     * @param output The place where all the output of the command - except errors - goes.
     * @throws InvalidCallOfCommandException Thrown if any errors occur during the execution of the command.
     */
    void tryToExecute(ICommand command, List<String> output) throws InvalidCallOfCommandException;
}