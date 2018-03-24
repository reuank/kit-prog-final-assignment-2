package task.olympia.commands;

import task.constructs.commands.CommandSignature;
import task.exceptions.IllegalCallOfCommandException;
import task.exceptions.ValidationException;
import task.interfaces.IRestrictedCommand;
import task.olympia.OlympiaApplication;
import task.olympia.models.OlympicSport;
import task.userinterface.auth.Permission;
import task.interfaces.ICommand;
import task.userinterface.auth.Session;

import java.util.List;

import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

/**
 * The list Olympic Sports Command
 */
public class ListOlympicSportsCommand implements IRestrictedCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature("list-olympic-sports");

    /**
     * Instantiates a new Olympic Sports Command
     * @param app the app the command operates on
     */
    public ListOlympicSportsCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    public void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        List<OlympicSport> olympicSports = this.app.getOlympicSportsSorted();
        List<String> serializedList = this.app.getSerializer().serializeOlympicSports(olympicSports);

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