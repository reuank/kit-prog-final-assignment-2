package task.olympia.commands;

import task.constructs.commands.CommandSignature;
import task.exceptions.ValidationException;
import task.interfaces.IRestrictedCommand;
import task.olympia.OlympiaApplication;
import task.olympia.models.IocCode;
import task.userinterface.auth.Permission;
import task.interfaces.ICommand;
import task.userinterface.auth.Session;

import java.util.List;

import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

/**
 * The List Ioc Codes Command
 */
public class ListIocCodesCommand implements IRestrictedCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature("list-ioc-codes");

    /**
     * Instantiates a new List Ioc Codes Command
     * @param app the app the command operates on
     */
    public ListIocCodesCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    public void restrictedExecution(ICommand command, List<String> outputStream) {
        List<IocCode> iocCodeList = this.app.getIocCodesSorted();
        List<String> serializedList = this.app.getSerializer().serializeIocCodes(iocCodeList);

        outputStream.addAll(serializedList);
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