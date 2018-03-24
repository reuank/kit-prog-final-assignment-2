package task.olympia.commands;

import task.constructs.program.CommandSignature;
import task.olympia.OlympiaApplication;
import task.userinterface.auth.Permission;
import task.interfaces.ICommand;

import java.util.List;

import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

public class ResetCommand extends AppCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature("reset");

    public ResetCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    void restrictedExecution(ICommand command, List<String> outputStream) {
        this.app.reset();

        outputStream.add("OK");
    }

    /**
     * @return - returns the app
     **/
    @Override
    public OlympiaApplication getApp() {
        return this.app;
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
