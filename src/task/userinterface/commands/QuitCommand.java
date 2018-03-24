package task.userinterface.commands;

import task.constructs.program.CommandSignature;
import task.exceptions.IllegalCallOfCommandException;
import task.exceptions.ValidationException;
import task.interfaces.ICommand;
import task.interfaces.IExecutableCommand;
import task.userinterface.CLI;

import java.util.List;

/**
 *
 */
public class QuitCommand implements IExecutableCommand {
    private CLI userInterface;
    private CommandSignature commandSignature = new CommandSignature("quit");

    /**
     * Instantiates a new Quit command that quits the olympia.
     * @param userInterface The userInterface in which the command is valid.
     */
    public QuitCommand(CLI userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void tryToExecute(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        try {
            this.userInterface.getInputValidator().validateCommand(command, this.commandSignature);

            userInterface.stop();
        } catch (ValidationException validationException) {
            throw new IllegalCallOfCommandException(
                    command.getSlug(),
                    this.commandSignature.getCommandSignature(),
                    validationException.getMessage()
            );
        }
    }

    /**
     * @return - returns the commandSignature
     * */
    @Override
    public CommandSignature getCommandSignature() {
        return this.commandSignature;
    }
}
