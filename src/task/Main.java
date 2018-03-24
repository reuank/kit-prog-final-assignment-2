package task;

import task.constructs.database.Database;
import task.constructs.database.Table;
import task.olympia.OlympiaApplication;
import task.olympia.commands.*;
import task.olympia.models.*;
import task.olympia.parser.AppParser;
import task.olympia.serializers.AppSerializer;
import task.userinterface.CLI;
import task.userinterface.commands.AddAdminCommand;
import task.userinterface.commands.LoginAdminCommand;
import task.userinterface.commands.LogoutAdminCommand;
import task.userinterface.commands.QuitCommand;
import task.userinterface.models.User;
import task.userinterface.parser.UserIntefaceParser;
import task.userinterface.validation.InputValidator;

/**
 * @author Leon Knauer
 *
 * General comments:
 * - Some of the defined functions are not used within this particular project.
 *     This is due to the goal of having as much modularity and therefore expandability as possible
 * - Some classes are meant as wrapper classes for simplifying the programming workflow.
 *     E.g.: By typing "getSerializer().", all the available Serializers are listed by Intellisense.
 * - Input processing workflow: Input -> Parsing / Validation -> Execution (if Executable command) -> Output
 */

public class Main {

    /**
     * Creates a new user interface with the corresponding user table,
     * creates a new olympia app with the corresponding tables,
     * registers all valid commands and runs the the user interface.
     *
     * @param args The passed commands args.
     */
    public static void main(String[] args) {
        /* ------------- SETUP USER INTERFACE ------------- */
        Database userDatabase = new Database();
        userDatabase.createTables(
                new Table<>(User.class)
        );

        CLI userInterface = new CLI(new UserIntefaceParser(), new InputValidator(), userDatabase);

        userInterface.registerCommands(
                new AddAdminCommand(userInterface),
                new LoginAdminCommand(userInterface),
                new LogoutAdminCommand(userInterface),
                new QuitCommand(userInterface)
        );

        /* ------------- SETUP APPLICATION ------------- */
        Database olympiaDatabase = new Database();
        olympiaDatabase.createTables(
                new Table<>(Athlete.class),
                new Table<>(Competition.class),
                new Table<>(IocCode.class),
                new Table<>(SportsVenue.class),
                new Table<>(OlympicSport.class)
        );

        OlympiaApplication app = new OlympiaApplication(
                olympiaDatabase,
                new AppParser(),
                new AppSerializer(),
                userInterface.getInputValidator(),
                userInterface.getSession()
        );

        userInterface.registerCommands(
                new AddAthleteCommand(app),
                new AddCompetitionCommand(app),
                new AddIocCodeCommand(app),
                new AddOlympicSportCommand(app),
                new AddSportsVenueCommand(app),
                new ListIocCodesCommand(app),
                new ListOlympicSportsCommand(app),
                new ListSportsVenuesCommand(app),
                new OlympicMedalTableCommand(app),
                new ResetCommand(app),
                new SummaryAthletesCommand(app)
        );

        /* ------------- RUN APPLICATION ------------- */
        userInterface.run();
    }
}
