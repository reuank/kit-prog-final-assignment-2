package task.olympia.commands;

import task.constructs.commands.Argument;
import task.constructs.commands.CommandSignature;
import task.exceptions.*;
import task.interfaces.IRestrictedCommand;
import task.olympia.OlympiaApplication;
import task.olympia.models.SportsVenue;
import task.userinterface.auth.Permission;
import task.interfaces.ICommand;
import task.userinterface.auth.Session;

import java.util.List;

import static task.constructs.commands.Datatype.STRING;
import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

/**
 * The List Sporty venues command
 */
public class ListSportsVenuesCommand implements IRestrictedCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature(
            "list-sports-venues",
            new Argument("country_name", STRING)
    );

    /**
     * Instantiates a new ListSportsVenue command
     * @param app the app the commands operate on
     */
    public ListSportsVenuesCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    public void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        List<SportsVenue> sportsVenueList = this.app.getSportsVenuesSorted(command.getArg(0));
        List<String> serializedList = this.app.getSerializer().serializeSportsVenues(sportsVenueList);

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