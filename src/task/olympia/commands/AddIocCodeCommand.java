package task.olympia.commands;

import task.constructs.commands.Argument;
import task.constructs.commands.CommandSignature;
import task.interfaces.IRestrictedCommand;
import task.exceptions.*;
import task.olympia.OlympiaApplication;
import task.olympia.models.IocCode;
import task.userinterface.auth.Permission;
import task.interfaces.ICommand;
import task.userinterface.auth.Session;

import java.util.List;

import static task.constructs.commands.Datatype.INT;
import static task.constructs.commands.Datatype.STRING;
import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

/**
 * The Add IOC Code command.
 */
public class AddIocCodeCommand implements IRestrictedCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature(
            "add-ioc-code",
            new Argument("id", STRING),
            new Argument("ioc_code", STRING),
            new Argument("country_name", STRING),
            new Argument("determination_year", INT)
    );

    /**
     * Instantiates a new add ioc code command.
     * @param app the app the command operates on.
     */
    public AddIocCodeCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    public void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        IocCode iocCode = this.app.getParser().parseIocCode(command.getArgs());
        this.app.addIocCode(iocCode);

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
        return this.app.getSession();
    }

    @Override
    public void validateCommand(ICommand command) throws ValidationException {
        this.app.getInputValidator().validateCommand(command, this.commandSignature);
    }
}