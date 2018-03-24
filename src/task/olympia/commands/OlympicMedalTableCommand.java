package task.olympia.commands;

import task.constructs.program.CommandSignature;
import task.exceptions.AuthException;
import task.exceptions.IllegalCallOfCommandException;
import task.exceptions.ValidationException;
import task.interfaces.IRestrictedCommand;
import task.olympia.OlympiaApplication;
import task.olympia.models.Athlete;
import task.olympia.models.OlympicMedalTableEntry;
import task.userinterface.auth.Permission;
import task.interfaces.ICommand;

import java.util.List;

import static task.userinterface.auth.Permission.MUST_BE_ADMIN;
import static task.userinterface.auth.Permission.MUST_BE_LOGGED_IN;

public class OlympicMedalTableCommand extends AppCommand {
    private OlympiaApplication app;
    private Permission[] requiredPermissions = new Permission[]{MUST_BE_LOGGED_IN, MUST_BE_ADMIN};
    private CommandSignature commandSignature = new CommandSignature("olympic-medal-table");

    /**
     * Instantiates a new Olympia Medal Table command.
     * @param app The app the command operates on.
     */
    public OlympicMedalTableCommand(OlympiaApplication app) {
        this.app = app;
    }

    @Override
    void restrictedExecution(ICommand command, List<String> outputStream) throws IllegalCallOfCommandException {
        List<OlympicMedalTableEntry> medalTable = this.app.getOlympiaMedalTable();
        List<String> serializedList = this.app.getSerializer().serializeOlympicMedalTable(medalTable);

        outputStream.addAll(serializedList);
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