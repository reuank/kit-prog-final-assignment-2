package task.olympia.commands;

import task.constructs.program.Argument;
import task.constructs.program.CommandSignature;
import task.exceptions.*;
import task.olympia.OlympiaApplication;
import task.olympia.models.Athlete;
import task.userinterface.auth.Permission;
import task.interfaces.ICommand;

import java.util.List;

import static task.constructs.program.Datatype.STRING;
import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

/**
 * The Add Athlete Command.
 */
public class AddAthleteCommand extends AppCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature(
            "add-athlete",
            new Argument("id", STRING),
            new Argument("firstname", STRING),
            new Argument("lastname", STRING),
            new Argument("country_name", STRING),
            new Argument("sport_type", STRING),
            new Argument("discipline", STRING)
    );

    /**
     * Instantiates a new add athlete command.
     * @param app the app the command operates on.
     */
    public AddAthleteCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        Athlete athlete = this.app.getParser().parseAthlete(command.getArgs());
        this.app.addAthlete(athlete);

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