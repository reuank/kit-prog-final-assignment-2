package task.userinterface.commands;

import task.constructs.program.CommandSignature;
import task.exceptions.AuthException;
import task.exceptions.InvalidCallOfCommandException;
import task.exceptions.ValidationException;
import task.interfaces.ICommand;
import task.interfaces.IExecutableCommand;
import task.interfaces.IRestrictedCommand;
import task.userinterface.auth.Permission;
import task.userinterface.validation.InputValidator;
import task.userinterface.CLI;

import java.util.List;

import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

/**
 *
 */
public class LogoutAdminCommand implements IExecutableCommand, IRestrictedCommand {
    private CLI userInterface;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature("logout-admin");

    /**
     * Instantiates a new Quit command that quits the olympia.
     * @param userInterface The userInterface in which the command is valid.
     */
    public LogoutAdminCommand(CLI userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public Permission[] getPermissionFlags() {
        return this.requiredPermissions;
    }

    @Override
    public void tryToExecute(ICommand command, List<String> outputStream) throws InvalidCallOfCommandException {
        try {
            this.checkPermissions(userInterface.getSession());

            this.userInterface.getInputValidator().validateCommand(command, this.commandSignature);

            this.userInterface.logout();

            outputStream.add("OK");
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException(
                    command.getSlug(),
                    this.commandSignature.getCommandSignature(),
                    validationException.getMessage()
            );
        } catch (AuthException authException) {
            throw new InvalidCallOfCommandException(authException.getMessage());
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
