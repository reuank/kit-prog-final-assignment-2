package task.userinterface.commands;

import task.constructs.program.CommandSignature;
import task.exceptions.InvalidCallOfCommandException;
import task.exceptions.ValidationException;
import task.olympia.validation.OlympiaValidator;
import task.interfaces.ICommand;
import task.interfaces.IExecutableCommand;
import task.userinterface.CLI;

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
    public void tryToExecute(ICommand command, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            OlympiaValidator.validateCommand(command, this.commandSignature);

            userInterface.stop();
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException(
                    command.getSlug(),
                    this.commandSignature.getCommandSignature(),
                    validationException.getMessage()
            );
        }
    }

    @Override
    public String getSlug() {
        return this.commandSignature.getSlug();
    }

    @Override
    public String[] getArgs() {
        return this.commandSignature.getArgNames();
    }

    @Override
    public String getArg(int index) {
        return null;
    }
}