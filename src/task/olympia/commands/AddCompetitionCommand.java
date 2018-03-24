package task.olympia.commands;

import task.constructs.commands.Argument;
import task.constructs.commands.CommandSignature;
import task.interfaces.IRestrictedCommand;
import task.exceptions.*;
import task.olympia.OlympiaApplication;
import task.olympia.models.Competition;
import task.userinterface.auth.Permission;
import task.interfaces.ICommand;
import task.userinterface.auth.Session;

import java.util.List;

import static task.constructs.commands.Datatype.INT;
import static task.constructs.commands.Datatype.STRING;
import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

/**
 * The Add competition command.
 */
public class AddCompetitionCommand implements IRestrictedCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature(
            "add-competition",
            new Argument("athlete id", STRING),
            new Argument("year", INT),
            new Argument("country name", STRING),
            new Argument("sport type", STRING),
            new Argument("discipline", STRING),
            new Argument("gold medal count", INT),
            new Argument("silver medal count", INT),
            new Argument("bronze medal count", INT)
    );

    /**
     * Instantiates a new Add competition command.
     * @param app the app the command operates on.
     */
    public AddCompetitionCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    public void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        Competition competition = this.app.getParser().parseCompetition(command.getArgs());
        this.app.addCompetition(competition);

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