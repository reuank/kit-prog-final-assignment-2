package task.userinterface.commands;

import task.constructs.commands.CommandSignature;
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
    public void executeCommand(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        this.userInterface.getInputValidator().validateCommand(command, this.commandSignature);

        userInterface.stop();
    }

    @Override
    public void validateCommand(ICommand command) throws ValidationException {
        this.userInterface.getInputValidator().validateCommand(command, this.commandSignature);
    }

    @Override
    public CommandSignature getCommandSignature() {
        return this.commandSignature;
    }
}
