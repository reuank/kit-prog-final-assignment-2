package task.userinterface.commands;

import task.constructs.program.CommandSignature;
import task.exceptions.AuthException;
import task.exceptions.IllegalCallOfCommandException;
import task.exceptions.ValidationException;
import task.interfaces.ICommand;
import task.interfaces.IExecutableCommand;
import task.interfaces.IRestrictedCommand;
import task.userinterface.auth.Permission;
import task.userinterface.CLI;
import task.userinterface.models.User;

import java.util.List;

import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;
import static task.userinterface.models.UserGroup.ADMIN;

/**
 *
 */
public class LogoutAdminCommand extends UiCommand {
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
    void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        this.userInterface.logout();

        outputStream.add("OK");
    }

    /**
     * @return - returns the user interface
     **/
    @Override
    public CLI getUi() {
        return this.userInterface;
    }

    /**
     * @return - returns the commandSignature
     * */
    @Override
    public CommandSignature getCommandSignature() {
        return this.commandSignature;
    }

    @Override
    public Permission[] getPermissionFlags() {
        return this.requiredPermissions;
    }
}
