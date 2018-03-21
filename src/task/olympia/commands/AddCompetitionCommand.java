package task.olympia.commands;

import task.constructs.program.Argument;
import task.constructs.program.CommandSignature;
import task.exceptions.AuthException;
import task.exceptions.InvalidCallOfCommandException;
import task.exceptions.ValidationException;
import task.interfaces.IRestrictedCommand;
import task.olympia.OlympiaApplication;
import task.userinterface.auth.Permission;
import task.userinterface.validation.InputValidator;
import task.interfaces.ICommand;
import task.interfaces.IExecutableCommand;

import static task.constructs.program.Datatype.INT;
import static task.constructs.program.Datatype.STRING;
import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

public class AddCompetitionCommand implements IExecutableCommand, IRestrictedCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature(
            "add-competition",
            new Argument("id", STRING),
            new Argument("athlete_id", STRING),
            new Argument("year", STRING),
            new Argument("country_name", STRING),
            new Argument("sport_type", STRING),
            new Argument("discipline", STRING),
            new Argument("gold", INT),
            new Argument("silver", INT),
            new Argument("bronze", INT)
    );

    public AddCompetitionCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    public Permission[] getPermissionFlags() {
        return this.requiredPermissions;
    }

    @Override
    public void tryToExecute(ICommand command, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            this.checkPermissions(this.app.getSession());

            this.app.getInputValidator().validateCommand(command, this.commandSignature);

            outputStream.append("OK");
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException(
                    command.getSlug(),
                    this.commandSignature.getCommandSignature(),
                    validationException.getMessage()
            );
        } catch (AuthException authException) {
            throw new InvalidCallOfCommandException(authException.getMessage());
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