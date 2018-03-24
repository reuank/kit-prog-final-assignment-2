package task.olympia.commands;

import task.constructs.program.Argument;
import task.constructs.program.CommandSignature;
import task.exceptions.*;
import task.interfaces.IRestrictedCommand;
import task.olympia.OlympiaApplication;
import task.olympia.models.Athlete;
import task.olympia.models.OlympicSport;
import task.userinterface.auth.Permission;
import task.interfaces.ICommand;
import task.interfaces.IExecutableCommand;

import java.util.List;

import static task.constructs.program.Datatype.STRING;
import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

public class AddOlympicSportCommand extends AppCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature(
            "add-olympic-sport",
            new Argument("sport_type", STRING),
            new Argument("discipline", STRING)
    );

    public AddOlympicSportCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        OlympicSport olympicSport = this.app.getParser().parseOlympicSport(command.getArg(0), command.getArg(1));
        this.app.addOlympicSport(olympicSport);

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