package task;

import task.constructs.database.Database;
import task.constructs.database.Table;
import task.olympia.OlympiaApplication;
import task.olympia.commands.*;
import task.olympia.models.*;
import task.olympia.parser.AppParser;
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
 * -    I intentionally designed this program in a framework-like way. By doing this, the olympia can easily be extended:
 *          -   If another command should be added, simply add a new command class, extend IExecutable and
 *              register it in main.
 *          -   If the number of paramters for commands changes, simply adjust the command signature accordingly.
 *          -   If in the future also parameters of another type are allowed, just expand the type validation in the
 *              "String ArrayValidation" class and adjust the command signature accordingly.
 *          -   If any rules of th olympia change, simply adjust the semantic command validation, the olympia option constants
 *              or tweak the olympia logic.
 *          -   If in the future there is the need for implementing translation, this can easily be done:
 *              All messages that are build using String.format, which allows calling a final constant as
 *              first parameter. This means that once all necessary constants are defined, implementing translation
 *              is very easy.
 *          -   Some of the defined functions are not used within this particular project.
 *              This is due to the goal of having as much modularity and therefore expandability as possible.
 *                  E.g.: The IntValidation class also provides the "isExactly()" check, which would be useful if the
 *                  rules of the olympia only allows one number of players one day.
*           -   Some classes are meant as wrapper classes for simplifying the programming workflow.
 *                  E.g.: By typing "ConnectSixSerializer.", all the available Serializers are listed by Intellisense.
 * -    Input processing workflow: Input -> Parsing -> Validation -> Execution (if Executable command) -> Output
 * -    The Executable commands are used for semantic validation and then passing the data to the olympia instance
 * @TODO Check what to do with command input where an semicolon appears
 */

public class Main {

    /**
     * Creates a new user interface and a new olympia, registers all valid commands and runs the the user interface.
     * @param args The passed program args.
     */
    public static void main(String[] args) {

        /* ------------- SETUP USER INTERFACE ------------- */
        Database userDatabase = new Database();
        userDatabase.createTable(
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
                new Table<>(SportDiscipline.class),
                new Table<>(SportsVenue.class),
                new Table<>(SportType.class)
        );

        OlympiaApplication app = new OlympiaApplication(
                new AppParser(),
                olympiaDatabase,
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
