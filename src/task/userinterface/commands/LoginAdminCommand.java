package task.userinterface.commands;

import task.constructs.program.Argument;
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

import static task.constructs.program.Datatype.STRING;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_OUT;
import static task.userinterface.models.UserGroup.ADMIN;

/**
 *
 */
public class LoginAdminCommand extends UiCommand {
    private CLI userInterface;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_OUT};
    private CommandSignature commandSignature = new CommandSignature(
            "login-admin",
            new Argument("username", STRING),
            new Argument("password", STRING)
    );

    /**
     * Instantiates a new Quit command that quits the olympia.
     * @param userInterface The userInterface in which the command is valid.
     */
    public LoginAdminCommand(CLI userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        this.userInterface.tryLogin(command.getArg(0), command.getArg(1));

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
