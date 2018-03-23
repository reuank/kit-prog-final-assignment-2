package task.olympia.commands;

import task.constructs.program.Argument;
import task.constructs.program.CommandSignature;
import task.exceptions.*;
import task.interfaces.IRestrictedCommand;
import task.olympia.OlympiaApplication;
import task.olympia.models.Competition;
import task.userinterface.auth.Permission;
import task.userinterface.validation.InputValidator;
import task.interfaces.ICommand;
import task.interfaces.IExecutableCommand;

import java.util.List;

import static task.constructs.program.Datatype.INT;
import static task.constructs.program.Datatype.STRING;
import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

public class AddCompetitionCommand implements IExecutableCommand, IRestrictedCommand {
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

    public AddCompetitionCommand(OlympiaApplication app) {
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

            Competition competition = this.app.getParser().parseCompetition(command.getArgs());
            this.app.addCompetition(competition);

            outputStream.add("OK");
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