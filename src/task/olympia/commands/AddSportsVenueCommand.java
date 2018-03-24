package task.olympia.commands;

import task.constructs.commands.Argument;
import task.constructs.commands.CommandSignature;
import task.interfaces.IRestrictedCommand;
import task.exceptions.*;
import task.olympia.OlympiaApplication;
import task.olympia.models.SportsVenue;
import task.userinterface.auth.Permission;
import task.interfaces.ICommand;
import task.userinterface.auth.Session;

import java.util.List;

import static task.constructs.commands.Datatype.INT;
import static task.constructs.commands.Datatype.STRING;
import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

/**
 * The Add Sports Venue Command
 */
public class AddSportsVenueCommand implements IRestrictedCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature(
            "add-sports-venue",
            new Argument("id", STRING),
            new Argument("country_name", STRING),
            new Argument("location", STRING),
            new Argument("name", STRING),
            new Argument("opening_year", INT),
            new Argument("seat_count", INT)
    );


    /**
     * Instantiates a new AddSportsVenue command
     * @param app the app the command shall operate on.
     */
    public AddSportsVenueCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    public void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        SportsVenue sportsVenue = this.app.getParser().parseSportsVenue(command.getArgs());
        this.app.addSportsVenue(sportsVenue);

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