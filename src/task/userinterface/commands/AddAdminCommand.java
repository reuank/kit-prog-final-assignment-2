package task.userinterface.commands;

import task.constructs.program.Argument;
import task.constructs.program.CommandSignature;
import task.exceptions.IllegalCallOfCommandException;
import task.interfaces.ICommand;
import task.userinterface.CLI;
import task.userinterface.auth.Permission;
import task.userinterface.models.User;

import java.util.List;

import static task.constructs.program.Datatype.STRING;
import static task.userinterface.models.UserGroup.ADMIN;

public class AddAdminCommand extends UiCommand {
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
    void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        User newUser = userInterface.getUiParser().parseUser(ADMIN, command.getArgs());
        this.userInterface.tryRegisterUser(newUser);

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