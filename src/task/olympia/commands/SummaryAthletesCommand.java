package task.olympia.commands;

import task.constructs.program.Argument;
import task.constructs.program.CommandSignature;
import task.exceptions.*;
import task.interfaces.IRestrictedCommand;
import task.olympia.OlympiaApplication;
import task.olympia.models.Athlete;
import task.olympia.models.AthleteSummary;
import task.olympia.models.OlympicSport;
import task.userinterface.auth.Permission;
import task.interfaces.ICommand;
import task.interfaces.IExecutableCommand;

import java.util.List;

import static task.constructs.program.Datatype.STRING;
import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

public class SummaryAthletesCommand implements IExecutableCommand, IRestrictedCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature(
            "summary-athletes",
            new Argument("sport_type", STRING),
            new Argument("discipline", STRING)
    );

    public SummaryAthletesCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    public Permission[] getPermissionFlags() {
        return this.requiredPermissions;
    }

    @Override
    public void tryToExecute(ICommand command, List<String> outputStream) throws InvalidCallOfCommandException {
        try {
            this.checkPermissions(this.app.getSession());

            this.app.getInputValidator().validateCommand(command, this.commandSignature);

            OlympicSport olympicSport = this.app.getParser().parseOlympicSport(command.getArgs());
            List<AthleteSummary> athleteList = this.app.getAthleteSummary(olympicSport);
            List<String> serializedList = this.app.getSerializer().serializeSummaryAthletes(athleteList);

            outputStream.addAll(serializedList);
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException(
                    command.getSlug(),
                    this.commandSignature.getCommandSignature(),
                    validationException.getMessage()
            );
        } catch (AuthException | DatabaseException | ParserException exception) {
            throw new InvalidCallOfCommandException(exception.getMessage());
        }
    }

    @Override
    public String getSlug() {
        return this.commandSignature.getSlug();
    }

    @Override
    public String[] getArgs() {
        return this.commandSignature.getArgNames();
    }

    @Override
    public String getArg(int index) {
        return this.commandSignature.getArgName(index);
    }
}