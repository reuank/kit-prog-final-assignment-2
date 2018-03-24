package task.userinterface.commands;

import task.exceptions.IllegalCallOfCommandException;
import task.exceptions.ValidationException;
import task.interfaces.ICommand;
import task.interfaces.IRestrictedCommand;
import task.userinterface.CLI;

import java.util.List;

public abstract class UiCommand implements IRestrictedCommand {
    /**
     *
     * @param cmd the command
     * @param stream the output stream.
     * @throws IllegalCallOfCommandException thrown if the execution went wrong.
     */
    abstract void restrictedExecution(ICommand cmd, List<String> stream) throws IllegalCallOfCommandException;

    /**
     * @return - returns the app the command operates on
     */
    abstract CLI getUi();

    @Override
    public void tryToExecute(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        try {
            this.checkPermissions(this.getUi().getSession());
            this.getUi().getInputValidator().validateCommand(command, getCommandSignature());

            restrictedExecution(command, outputStream);
        } catch (ValidationException validationException) {
            throw new IllegalCallOfCommandException(
                    command.getSlug(),
                    this.getCommandSignature().getCommandSignature(),
                    validationException.getMessage()
            );
        }
    }

}
