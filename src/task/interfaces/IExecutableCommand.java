package task.interfaces;

import task.constructs.program.CommandSignature;
import task.exceptions.IllegalCallOfCommandException;

import java.util.List;

/**
 * An interface for executable commands.
 */
public interface IExecutableCommand extends ICommand {
    /**
     * Tries to execute the given command using the processes defined in the database command class.
     * @param command The command that shall be executed.
     * @param output The place where all the output of the command - except errors - goes.
     * @throws IllegalCallOfCommandException Thrown if any errors occur during the execution of the command.
     */
    void tryToExecute(ICommand command, List<String> output) throws IllegalCallOfCommandException;

    /**
     * @return - returns the command signature.
     */
    CommandSignature getCommandSignature();

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