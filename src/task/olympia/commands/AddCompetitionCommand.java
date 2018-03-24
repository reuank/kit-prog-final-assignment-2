package task.olympia.commands;

import task.constructs.program.Argument;
import task.constructs.program.CommandSignature;
import task.exceptions.*;
import task.olympia.OlympiaApplication;
import task.olympia.models.Competition;
import task.userinterface.auth.Permission;
import task.interfaces.ICommand;

import java.util.List;

import static task.constructs.program.Datatype.INT;
import static task.constructs.program.Datatype.STRING;
import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

/**
 * The Add competition command.
 */
public class AddCompetitionCommand extends AppCommand {
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
    void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        Competition competition = this.app.getParser().parseCompetition(command.getArgs());
        this.app.addCompetition(competition);

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