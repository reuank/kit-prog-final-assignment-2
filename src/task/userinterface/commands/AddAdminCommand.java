package task.userinterface.commands;

import task.constructs.commands.Argument;
import task.constructs.commands.CommandSignature;
import task.exceptions.IllegalCallOfCommandException;
import task.exceptions.ValidationException;
import task.interfaces.ICommand;
import task.interfaces.IRestrictedCommand;
import task.userinterface.CLI;
import task.userinterface.auth.Permission;
import task.userinterface.auth.Session;
import task.userinterface.models.User;

import java.util.List;

import static task.constructs.commands.Datatype.STRING;
import static task.userinterface.auth.UserGroup.ADMIN;

/**
 * The add admin command.
 */
public class AddAdminCommand implements IRestrictedCommand {
    private CLI userInterface;
    private Permission[] requiredPermissions = new Permission[]{};
    private CommandSignature commandSignature = new CommandSignature(
            "add-admin",
            new Argument("firstname", STRING),
            new Argument("lastname", STRING),
            new Argument("username", STRING),
            new Argument("password", STRING)
    );

    /**
     * Instantiates a new add admin command.
     * @param userInterface the user interface.
     */
    public AddAdminCommand(CLI userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        User newUser = userInterface.getUiParser().parseUser(ADMIN, command.getArgs());
        this.userInterface.tryRegisterUser(newUser);

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