package task.userinterface.commands;

import task.constructs.commands.Argument;
import task.constructs.commands.CommandSignature;
import task.exceptions.IllegalCallOfCommandException;
import task.exceptions.ValidationException;
import task.interfaces.ICommand;
import task.interfaces.IRestrictedCommand;
import task.userinterface.auth.Permission;
import task.userinterface.CLI;
import task.userinterface.auth.Session;

import java.util.List;

import static task.constructs.commands.Datatype.STRING;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_OUT;

/**
 * The login admin command
 */
public class LoginAdminCommand implements IRestrictedCommand {
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
    public void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        this.userInterface.tryLogin(command.getArg(0), command.getArg(1));

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
