package task.olympia.commands;

import task.constructs.commands.Argument;
import task.constructs.commands.CommandSignature;
import task.interfaces.IRestrictedCommand;
import task.exceptions.*;
import task.olympia.OlympiaApplication;
import task.olympia.models.OlympicSport;
import task.userinterface.auth.Permission;
import task.interfaces.ICommand;
import task.userinterface.auth.Session;

import java.util.List;

import static task.constructs.commands.Datatype.STRING;
import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

/**
 * The AddOlympicSportCommand
 */
public class AddOlympicSportCommand implements IRestrictedCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature(
            "add-olympic-sport",
            new Argument("sport_type", STRING),
            new Argument("discipline", STRING)
    );

    /**
     * Instantiates a new AddOlympicSport command
     * @param app the app the command shall operate on
     */
    public AddOlympicSportCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    public void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        OlympicSport olympicSport = this.app.getParser().parseOlympicSport(command.getArg(0), command.getArg(1));
        this.app.addOlympicSport(olympicSport);

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