package task.userinterface.commands;

import task.constructs.commands.CommandSignature;
import task.exceptions.IllegalCallOfCommandException;
import task.exceptions.ValidationException;
import task.interfaces.ICommand;
import task.interfaces.IRestrictedCommand;
import task.userinterface.auth.Permission;
import task.userinterface.CLI;
import task.userinterface.auth.Session;

import java.util.List;

import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

/**
 *
 */
public class LogoutAdminCommand implements IRestrictedCommand {
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
    public void restrictedExecution(ICommand command, List<String> outputStream) {
        this.userInterface.logout();

        outputStream.add("OK");
    }

    @Override
    public CommandSignature getCommandSignature() {
        return this.commandSignature;
    }

    @Override
    public Permission[] getPermissionFlags() {
        return this.requiredPermissions;
    }

    @Override
    public Session getSession() {
        return this.userInterface.getSession();
    }

    @Override
    public void validateCommand(ICommand command) throws ValidationException {
        this.userInterface.getInputValidator().validateCommand(command, this.commandSignature);
    }
}
