package task.userinterface.commands;

import task.constructs.program.Argument;
import task.constructs.program.CommandSignature;
import task.exceptions.AuthException;
import task.exceptions.InvalidCallOfCommandException;
import task.exceptions.ValidationException;
import task.olympia.validation.OlympiaValidator;
import task.interfaces.ICommand;
import task.interfaces.IExecutableCommand;
import task.userinterface.CLI;
import task.userinterface.models.User;

import static task.constructs.program.Datatype.STRING;
import static task.userinterface.models.UserGroup.ADMIN;

public class AddAdminCommand implements IExecutableCommand {
    private CLI userInterface;
    private CommandSignature commandSignature = new CommandSignature(
            "add-admin",
            new Argument("firstname", STRING),
            new Argument("lastname", STRING),
            new Argument("username", STRING),
            new Argument("password", STRING)
    );

    public AddAdminCommand(CLI userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void tryToExecute(ICommand command, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            // Check the passed command against the signature it should have
            OlympiaValidator.validateCommand(command, this.commandSignature);

            User newUser = new User(ADMIN, command.getArgs());
            this.userInterface.registerUser(newUser);

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