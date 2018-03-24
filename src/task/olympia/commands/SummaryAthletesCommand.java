package task.olympia.commands;

import task.constructs.commands.Argument;
import task.constructs.commands.CommandSignature;
import task.exceptions.*;
import task.interfaces.IRestrictedCommand;
import task.olympia.OlympiaApplication;
import task.olympia.models.AthleteSummaryEntry;
import task.olympia.models.OlympicSport;
import task.userinterface.auth.Permission;
import task.interfaces.ICommand;
import task.userinterface.auth.Session;

import java.util.List;

import static task.constructs.commands.Datatype.STRING;
import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

/**
 * The Summary Athletes Command
 */
public class SummaryAthletesCommand implements IRestrictedCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature(
            "summary-athletes",
            new Argument("sport_type", STRING),
            new Argument("discipline", STRING)
    );

    /**
     * Instantiates a new Summary Athletes Command
     * @param app the app the command operates on
     */
    public SummaryAthletesCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    public void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        OlympicSport olympicSport = this.app.getParser().parseOlympicSport(command.getArgs());
        List<AthleteSummaryEntry> athleteList = this.app.getAthleteSummary(olympicSport);
        List<String> serializedList = this.app.getSerializer().serializeSummaryAthletes(athleteList);

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